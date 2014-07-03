package com.foodapp.app;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;

public class BaseNavigationActivity extends FragmentActivity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getFragmentManager();
    }

    protected void setupDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) mFragmentManager.findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
    }

    protected void enableNavigationDrawer(boolean enabled) {
        mNavigationDrawerFragment.enableNavigationDrawer(enabled);
    }

    protected void openDrawer() {
        mNavigationDrawerFragment.openDrawer();
    }
}
