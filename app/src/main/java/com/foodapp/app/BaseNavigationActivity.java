package com.foodapp.app;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;

import com.foodapp.app.drawer.NavigationDrawerFragment;
import com.foodapp.app.sections.main.enums.DishType;
import com.foodapp.app.sections.main.listeners.OnFilterGalleryRequestedListener;
import com.foodapp.app.sections.settings.SettingsActivity;
import com.foodapp.app.utils.ToastUtils;

import java.util.Map;

public class BaseNavigationActivity extends FragmentActivity implements
        NavigationDrawerFragment.NavigationDrawerContainer {

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private FragmentManager mFragmentManager;

    private OnFilterGalleryRequestedListener mGalleryRequestedListener;
    private Map<String, String> mOperationToExecute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFragmentManager = getFragmentManager();
    }

    protected void setupDrawer() {
        mNavigationDrawerFragment = (NavigationDrawerFragment) mFragmentManager.findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    protected void enableNavigationDrawer(boolean enabled) {
        mNavigationDrawerFragment.enableNavigationDrawer(enabled);
    }

    protected void openDrawer() {
        mNavigationDrawerFragment.openDrawer();
    }

    protected void lockDrawer() {
        mNavigationDrawerFragment.lockDrawer();
    }

    protected void unlockDrawer() {
        mNavigationDrawerFragment.unlockDrawer();
    }

    protected void addGalleryRequestedListener(OnFilterGalleryRequestedListener listener) {
        mGalleryRequestedListener = listener;
    }

    protected void removeGalleryRequestedListener() {
        mGalleryRequestedListener = null;
    }

    @Override
    public void onGallerySectionRequested(DishType dishType) {
        if (mGalleryRequestedListener != null) {
            mGalleryRequestedListener.onFilterGalleryRequested(dishType);
        } else {
            Intent galleryIntent = new Intent(this, MainActivity.class);
            galleryIntent.putExtra(DishType.DISH_TYPE, dishType.getDishType());
            galleryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(galleryIntent);
        }
    }

    @Override
    public void onSettingsSectionRequested() {
        Intent settingsIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsIntent);
    }

    @Override
    public void onSendFeedbackRequested(String reason, String message) {
        ToastUtils.showToastShort(this, "Send an email for: " + reason + ", with message: " + message);
    }
}
