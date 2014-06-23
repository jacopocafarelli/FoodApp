package com.foodapp.app.sections.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.foodapp.app.R;
import com.foodapp.app.common.OnBackPressedListener;
import com.foodapp.app.sections.main.listeners.OnTakePictureRequestedListener;

import java.util.ArrayList;

public class MainContainerFragment extends Fragment implements
        OnBackPressedListener,
        GalleryFragment.FragmentContainer,
        CameraFragment.FragmentContainer {

    private static final int ANIMATION_DURATION_MS = 2000;
    private static final String SAVED_INSTANCE_CAMERA_IS_SHOWING = "SAVED_INSTANCE_CAMERA_IS_SHOWING";
    private static final String SAVED_INSTANCE_INITIAL_BUTTON_POSITION = "SAVED_INSTANCE_INITIAL_BUTTON_POSITION";

    private FragmentContainer mFragmentContainer;
    private Button mTakePictureButton;

    private boolean mIsShowingCamera;
    private int mInitialButtonY = -1;
    private ArrayList<OnTakePictureRequestedListener> mPictureRequestedListeners;

    public static MainContainerFragment newInstance() {
        MainContainerFragment fragment = new MainContainerFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Fragment parentFragment = getParentFragment();
        if (parentFragment == null) {
            mFragmentContainer = (FragmentContainer) activity;
        } else {
            mFragmentContainer = (FragmentContainer) parentFragment;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            FragmentManager fm = getChildFragmentManager();

            Fragment galleryContentFragment = GalleryFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fl_fragment_gallery_content, galleryContentFragment)
                    .commit();
        } else {
            mIsShowingCamera = savedInstanceState.getBoolean(SAVED_INSTANCE_CAMERA_IS_SHOWING);
            mInitialButtonY = savedInstanceState.getInt(SAVED_INSTANCE_INITIAL_BUTTON_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_container, container, false);

        mPictureRequestedListeners = new ArrayList<OnTakePictureRequestedListener>();
        mTakePictureButton = (Button) view.findViewById(R.id.btn_fragment_gallery_container);
        mTakePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mIsShowingCamera) {
                    calculateButtonPosition();
                    showCameraFragment();
                } else {
                    notifyCameraListeners();
                    mIsShowingCamera = false;
                }
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_INSTANCE_CAMERA_IS_SHOWING, mIsShowingCamera);
        outState.putInt(SAVED_INSTANCE_INITIAL_BUTTON_POSITION, mInitialButtonY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mTakePictureButton = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentContainer = null;
    }

    @Override
    public boolean onBackPressed() {
        if (mIsShowingCamera) {
            mIsShowingCamera = false;
            mTakePictureButton.animate().setDuration(ANIMATION_DURATION_MS).y(mInitialButtonY);
        }
        return getChildFragmentManager().popBackStackImmediate();
    }

    private void calculateButtonPosition() {
        if (mInitialButtonY == -1) {
            int buttonCoords[] = new int[2];
            mTakePictureButton.getLocationOnScreen(buttonCoords);
            int buttonHeight = mTakePictureButton.getHeight();
            mInitialButtonY = buttonCoords[1] - (buttonHeight + buttonHeight / 2);
        }
    }

    private void showCameraFragment() {
        FragmentManager fm = getChildFragmentManager();

        Fragment cameraFragment = CameraFragment.newInstance();
        fm.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                .add(R.id.fl_fragment_gallery_content, cameraFragment)
                .addToBackStack("")
                .commit();
    }

    private void notifyCameraListeners() {
        for (int i = 0; i < mPictureRequestedListeners.size(); i++) {
            mPictureRequestedListeners.get(i).onTakePictureRequested();
        }
    }

    @Override
    public void addTakePictureRequestedListener(OnTakePictureRequestedListener listener) {
        mPictureRequestedListeners.add(listener);
    }

    @Override
    public void removeTakePictureRequestedListener(OnTakePictureRequestedListener listener) {
        mPictureRequestedListeners.remove(listener);
    }

    @Override
    public void notifyCameraIsShowing() {
        mIsShowingCamera = true;
        mTakePictureButton.animate().setDuration(ANIMATION_DURATION_MS).y(1000);
    }

    public interface FragmentContainer {

    }
}
