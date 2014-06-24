package com.foodapp.app;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;

import com.foodapp.app.common.OnBackPressedListener;
import com.foodapp.app.sections.main.MainContainerFragment;

public class MainActivity extends FragmentActivity implements
        MainContainerFragment.FragmentContainer,
        NavigationDrawerFragment.NavigationDrawerCallbacks {
//        , MainFragment.OnFragmentInteractionListener

    private static final String GALLERY_FRAGMENT_TAG = "GALLERY_FRAGMENT_TAG";
    //    private static final String SELECTED_CATEGORY = "SELECTED_CATEGORY";
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;
    private FragmentManager mFragmentManager;
    private MainContainerFragment mGalleryFragment;
    private int mSelectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        mFragmentManager = getSupportFragmentManager();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        handleFragmentVisibility(savedInstanceState);
    }


    private void handleFragmentVisibility(Bundle savedInstanceState) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (savedInstanceState == null) {
            mGalleryFragment = MainContainerFragment.newInstance();
            fragmentTransaction.add(R.id.container, mGalleryFragment, GALLERY_FRAGMENT_TAG).commit();
        } else {
            mGalleryFragment = (MainContainerFragment) mFragmentManager.findFragmentByTag(GALLERY_FRAGMENT_TAG);
        }
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment instanceof OnBackPressedListener && ((OnBackPressedListener) fragment).onBackPressed()) {
            return;
        } else {
            super.onBackPressed();
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        outState.putInt(SELECTED_CATEGORY, mSelectedCategory);
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
