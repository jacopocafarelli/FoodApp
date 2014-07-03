package com.foodapp.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.foodapp.app.sections.main.CameraFragment;
import com.foodapp.app.sections.main.GalleryFragment;
import com.foodapp.app.sections.main.enums.DishType;
import com.foodapp.app.sections.main.listeners.OnFilterGalleryRequestedListener;
import com.foodapp.app.sections.main.listeners.OnTakePictureRequestedListener;
import com.foodapp.app.utils.DisplayMetricsUtils;
import com.foodapp.app.utils.ToastUtils;

public class MainActivity extends BaseNavigationActivity implements
        OnFilterGalleryRequestedListener,
        GalleryFragment.FragmentContainer,
        CameraFragment.FragmentContainer {
//        , MainFragment.OnFragmentInteractionListener

    private static final int ANIMATION_DURATION_MS = 1000;
    private static final int DOUBLE_BACK_PRESSED_GAP_MS = 3000;
    private static final String SAVED_INSTANCE_CAMERA_IS_SHOWING = "SAVED_INSTANCE_CAMERA_IS_SHOWING";
    private static final String SAVED_INSTANCE_BUTTON_GALLERY_POSITION = "SAVED_INSTANCE_BUTTON_GALLERY_POSITION";
    private static final String SAVED_INSTANCE_BUTTON_CAMERA_POSITION = "SAVED_INSTANCE_BUTTON_CAMERA_POSITION";
    private static final String GALLERY_FRAGMENT_TAG = "GALLERY_FRAGMENT_TAG";
    private static final String CAMERA_FRAGMENT_TAG = "CAMERA_FRAGMENT_TAG";

    private boolean mFirstBackPressed = false;
    private boolean mIsShowingCamera;
    private int mGalleryButtonPositionY = -1;
    private int mCameraButtonPositionY = -1;
    private OnTakePictureRequestedListener mPictureRequestedListener;

    private FragmentManager mFragmentManager;
    private GalleryFragment mGalleryFragment;
    private Button mTakePictureButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        mFragmentManager = getSupportFragmentManager();
        setupDrawer();
        mTakePictureButton = (Button) findViewById(R.id.btn_activity_main);
        mTakePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsShowingCamera) {
                    calculateButtonGalleryPosition();
                    calculateButtonCameraPosition();
                    showCameraFragment();
                } else {
                    notifyCameraListener();
                    mIsShowingCamera = false;
                }
            }
        });

        if (savedInstanceState != null) {
            mIsShowingCamera = savedInstanceState.getBoolean(SAVED_INSTANCE_CAMERA_IS_SHOWING);
            mGalleryButtonPositionY = savedInstanceState.getInt(SAVED_INSTANCE_BUTTON_GALLERY_POSITION);
            mCameraButtonPositionY = savedInstanceState.getInt(SAVED_INSTANCE_BUTTON_CAMERA_POSITION);
        }

        handleFragmentVisibility(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        addGalleryRequestedListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        removeGalleryRequestedListener();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mIsShowingCamera) {
                    onBackPressed();
                } else {
                    openDrawer();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_INSTANCE_CAMERA_IS_SHOWING, mIsShowingCamera);
        outState.putInt(SAVED_INSTANCE_BUTTON_GALLERY_POSITION, mGalleryButtonPositionY);
        outState.putInt(SAVED_INSTANCE_BUTTON_CAMERA_POSITION, mCameraButtonPositionY);
    }

    private void handleFragmentVisibility(Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (savedInstanceState == null) {
            mGalleryFragment = GalleryFragment.newInstance();
            fragmentTransaction.add(R.id.container, mGalleryFragment, GALLERY_FRAGMENT_TAG).commit();
        } else {
            mIsShowingCamera = savedInstanceState.getBoolean(SAVED_INSTANCE_CAMERA_IS_SHOWING);
            mGalleryButtonPositionY = savedInstanceState.getInt(SAVED_INSTANCE_BUTTON_GALLERY_POSITION);
            mCameraButtonPositionY = savedInstanceState.getInt(SAVED_INSTANCE_BUTTON_CAMERA_POSITION);
            mGalleryFragment = (GalleryFragment) mFragmentManager.findFragmentByTag(GALLERY_FRAGMENT_TAG);
        }
    }

    @Override
    public void onBackPressed() {
        if (mIsShowingCamera) {
            enableNavigationDrawer(true);
            unlockDrawer();
            mIsShowingCamera = false;
            mTakePictureButton.animate().setDuration(ANIMATION_DURATION_MS).y(mGalleryButtonPositionY);
            mFragmentManager.popBackStackImmediate();
        } else {
            if (mFirstBackPressed) {
                super.onBackPressed();
            } else {
                handleDoubleBackPressed();
            }
        }
    }

    private void handleDoubleBackPressed() {
        ToastUtils.showToastShort(this, getString(R.string.toast_exit_application));
        mFirstBackPressed = true;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                mFirstBackPressed = false;
            }
        }, DOUBLE_BACK_PRESSED_GAP_MS);
    }

    private void calculateButtonGalleryPosition() {
        if (mGalleryButtonPositionY == -1) {
            int buttonCoords[] = new int[2];
            mTakePictureButton.getLocationOnScreen(buttonCoords);
            int buttonHeight = mTakePictureButton.getHeight();
            mGalleryButtonPositionY = buttonCoords[1] - (buttonHeight + buttonHeight / 2);
        }
    }

    private void calculateButtonCameraPosition() {
        if (mCameraButtonPositionY == -1) {
            int statusBarHeight = DisplayMetricsUtils.getStatusBarHeight(this);
            int actionBarHeight = DisplayMetricsUtils.getActionBarHeight(this);
            int cameraMargins = getResources().getDimensionPixelSize(R.dimen.camera_preview_margin_top_bottom) * 2;
            int cameraPreviewHeight = DisplayMetricsUtils.getDisplayWidth(this) + cameraMargins;
            int cameraButtonHeight = mTakePictureButton.getHeight();
            mCameraButtonPositionY = (statusBarHeight + actionBarHeight + cameraPreviewHeight) - (cameraButtonHeight / 2);
        }
    }

    private void showCameraFragment() {
        Fragment cameraFragment = CameraFragment.newInstance();
        mFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out, R.anim.fade_in, R.anim.fade_out)
                .add(R.id.container, cameraFragment)
                .addToBackStack(CAMERA_FRAGMENT_TAG)
                .commit();
    }

    private void notifyCameraListener() {
        if (mPictureRequestedListener != null) {
            mPictureRequestedListener.onTakePictureRequested();
        }
    }

    private void showFilteredGallery(DishType dishType) {
        if (mIsShowingCamera) {
            onBackPressed();
        }
        ToastUtils.showToastShort(this, "We should show filtered results here");
    }

    @Override
    public void addTakePictureRequestedListener(OnTakePictureRequestedListener listener) {
        mPictureRequestedListener = listener;
    }

    @Override
    public void removeTakePictureRequestedListener(OnTakePictureRequestedListener listener) {
        mPictureRequestedListener = null;
    }

    @Override
    public void notifyCameraIsShowing() {
        lockDrawer();
        enableNavigationDrawer(false);
        mIsShowingCamera = true;
        mTakePictureButton.animate().setDuration(ANIMATION_DURATION_MS).y(mCameraButtonPositionY);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String dishTypeString = intent.getExtras().getString(DishType.DISH_TYPE);
        DishType dishType = DishType.valueOf(dishTypeString);
        showFilteredGallery(dishType);
    }

    @Override
    public void onFilterGalleryRequested(DishType dishType) {
        showFilteredGallery(dishType);
    }
}
