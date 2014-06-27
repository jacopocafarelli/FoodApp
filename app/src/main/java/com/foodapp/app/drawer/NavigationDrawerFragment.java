package com.foodapp.app.drawer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.foodapp.app.R;
import com.foodapp.app.drawer.operations.Executable;
import com.foodapp.app.drawer.operations.FeedbackOperation;
import com.foodapp.app.drawer.operations.GalleryOperation;
import com.foodapp.app.drawer.operations.SettingsOperation;
import com.foodapp.app.sections.main.enums.DishType;

/**
 * Fragment used for managing interactions for and presentation of a navigation drawer.
 * See the <a href="https://developer.android.com/design/patterns/navigation-drawer.html#Interaction">
 * design guidelines</a> for a complete explanation of the behaviors implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /* Open the drawer the first time if so the user knows that there's one */
    private static final String PREF_USER_LEARNED_DRAWER = "PREF_USER_LEARNED_DRAWER";
    /* These two string are not resources because will only be used as email subjects */
    private static final String EMAIL_FEATURE_REQUEST_SUBJECT = " - Feature Request";
    private static final String EMAIL_BUG_REPORT_SUBJECT = " - Bug Report";

    /* A pointer to the current callbacks instance (the Activity). */
    private NavigationDrawerContainer mNavigationDrawerContainer;

    /* Helper component that ties the action bar to the navigation drawer. */
    private ActionBarDrawerToggle mDrawerToggle;

    private DrawerLayout mDrawerLayout;
    private View mFragmentContainerView;

    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    private Executable mExecutable;

    public static NavigationDrawerFragment newInstance() {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mNavigationDrawerContainer = (NavigationDrawerContainer) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View navigationDrawer = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);

        setUpGalleryButton(navigationDrawer);
        setUpGalleryCategories(navigationDrawer);
        setUpSettingsButton(navigationDrawer);
        setUpContactButtons(navigationDrawer);

        return navigationDrawer;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDrawerLayout = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNavigationDrawerContainer = null;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void setUpGalleryButton(View drawerLayout) {
        LinearLayout galleryButton = (LinearLayout) drawerLayout.findViewById(R.id.ll_fragment_navigation_drawer_gallery);
        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExecutable = new GalleryOperation(mNavigationDrawerContainer, DishType.NONE);
                closeDrawer();
            }
        });
    }

    private void setUpGalleryCategories(View drawerLayout) {
        LinearLayout galleryCategoriesContainer = (LinearLayout) drawerLayout.findViewById(R.id.ll_fragment_navigation_drawer_gallery_categories);
        // TODO use a for instead
        addGalleryCategory(galleryCategoriesContainer);
        addGalleryCategory(galleryCategoriesContainer);
        addGalleryCategory(galleryCategoriesContainer);
        addGalleryCategory(galleryCategoriesContainer);
        addGalleryCategory(galleryCategoriesContainer);
        addGalleryCategory(galleryCategoriesContainer);
        addGalleryCategory(galleryCategoriesContainer);
        addGalleryCategory(galleryCategoriesContainer);
        addGalleryCategory(galleryCategoriesContainer);
    }

    private void addGalleryCategory(LinearLayout container) {
        View categoryLayout = LayoutInflater.from(getActivity()).inflate(R.layout.item_navigation_drawer_dish_categories, null);
        container.addView(categoryLayout);
    }

    private void setUpSettingsButton(View drawerLayout) {
        LinearLayout settingsLayout = (LinearLayout) drawerLayout.findViewById(R.id.ll_fragment_navigation_drawer_settings);
        settingsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExecutable = new SettingsOperation(mNavigationDrawerContainer);
                closeDrawer();
            }
        });
    }

    private void setUpContactButtons(View drawerLayout) {
        final String appName = getResources().getString(R.string.app_name);
        final String featureRequestMessage = getResources().getString(R.string.email_message_feature_request);
        final String bugReportMessage = getResources().getString(R.string.email_message_bug_report, getApkVersion());
        LinearLayout contactUsLayout = (LinearLayout) drawerLayout.findViewById(R.id.ll_fragment_navigation_drawer_contact_us);
        contactUsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExecutable = new FeedbackOperation(mNavigationDrawerContainer, appName, "");
                closeDrawer();
            }
        });
        LinearLayout featureRequestLayout = (LinearLayout) drawerLayout.findViewById(R.id.ll_fragment_navigation_drawer_feature_request);
        featureRequestLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExecutable = new FeedbackOperation(mNavigationDrawerContainer, appName + EMAIL_FEATURE_REQUEST_SUBJECT, featureRequestMessage);
                closeDrawer();
            }
        });
        LinearLayout bugReportLayout = (LinearLayout) drawerLayout.findViewById(R.id.ll_fragment_navigation_drawer_bug_report);
        bugReportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mExecutable = new FeedbackOperation(mNavigationDrawerContainer, appName + EMAIL_BUG_REPORT_SUBJECT, bugReportMessage);
                closeDrawer();
            }
        });
    }

    private String getApkVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            return " (v" + info.versionName + ")";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.drawable.ic_drawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu();

                if (mExecutable != null) {
                    mExecutable.execute();
                    mExecutable = null;
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this flag to prevent auto-showing
                    // the navigation drawer automatically in the future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu();
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void enableNavigationDrawer(boolean enabled) {
        mDrawerToggle.setDrawerIndicatorEnabled(enabled);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(mFragmentContainerView);
    }

    public void closeDrawer() {
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    public void lockDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void unlockDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    /* Callbacks interface that all activities using this fragment must implement. */
    public static interface NavigationDrawerContainer {
        void onGallerySectionRequested(DishType dishType);

        void onSendFeedbackRequested(String reason, String message);

        void onSettingsSectionRequested();
    }
}
