package com.foodapp.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodapp.app.fragments.MainFragment;


public class MainActivity extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks, MainFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;

    private int mSelectedCategory;

    private static final String MAIN_FRAGMENT = "MAIN_FRAGMENT";
    private static final String SELECTED_CATEGORY = "SELECTED_CATEGORY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
        mFragmentManager = getFragmentManager();

        handleFragmentVisibility(savedInstanceState);

    }


    private void handleFragmentVisibility(Bundle savedInstance) {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        // Get fragments by tag
        mMainFragment = (MainFragment) mFragmentManager.findFragmentByTag(MAIN_FRAGMENT);

        if (mMainFragment == null) {
            mMainFragment = MainFragment.newInstance(0);

            if (savedInstance == null) {
                fragmentTransaction.add(R.id.container, mMainFragment, MAIN_FRAGMENT);
                fragmentTransaction.show(mMainFragment);
                fragmentTransaction.commit();
            } else {
                mSelectedCategory = savedInstance.getInt(SELECTED_CATEGORY);
                mMainFragment.setCategoryAndRefresh(mSelectedCategory);
            }

        }

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        mSelectedCategory = position;
        // update the main content
        if (mMainFragment != null)
            mMainFragment.setCategoryAndRefresh(position);
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
        outState.putInt(SELECTED_CATEGORY, mSelectedCategory);
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
