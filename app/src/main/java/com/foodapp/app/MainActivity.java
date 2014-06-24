package com.foodapp.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;

import com.foodapp.app.common.OnBackPressedListener;
import com.foodapp.app.sections.main.MainContainerFragment;
import com.foodapp.app.utils.ToastUtils;

public class MainActivity extends Activity implements
        MainContainerFragment.FragmentContainer,
        NavigationDrawerFragment.NavigationDrawerCallbacks {
//        , MainFragment.OnFragmentInteractionListener

    private static final String GALLERY_FRAGMENT_TAG = "GALLERY_FRAGMENT_TAG";
    private static final int DOUBLE_BACK_PRESSED_GAP_MS = 3000;
    //    private static final String SELECTED_CATEGORY = "SELECTED_CATEGORY";

    private CharSequence mTitle;
    private boolean mFirstBackPressed = false;
    private int mSelectedCategory;

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FragmentManager mFragmentManager;
    private MainContainerFragment mMainContainerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentManager = getFragmentManager();
        mNavigationDrawerFragment = (NavigationDrawerFragment) mFragmentManager.findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        handleFragmentVisibility(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt(SELECTED_CATEGORY, mSelectedCategory);
    }

    private void handleFragmentVisibility(Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (savedInstanceState == null) {
            mMainContainerFragment = MainContainerFragment.newInstance();
            fragmentTransaction.add(R.id.container, mMainContainerFragment, GALLERY_FRAGMENT_TAG).commit();
        } else {
            mMainContainerFragment = (MainContainerFragment) mFragmentManager.findFragmentByTag(GALLERY_FRAGMENT_TAG);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof OnBackPressedListener && ((OnBackPressedListener) fragment).onBackPressed()) {
            return;
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

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mSelectedCategory = position;
        // update the main content
//        if (mMainFragment != null)
//            mMainFragment.setCategoryAndRefresh(position);
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

//    @Override
//    public void onFragmentInteraction(Uri uri) {}
}
