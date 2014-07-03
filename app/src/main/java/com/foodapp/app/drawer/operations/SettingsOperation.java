package com.foodapp.app.drawer.operations;

import com.foodapp.app.drawer.NavigationDrawerFragment;

public class SettingsOperation implements Executable {

    private NavigationDrawerFragment.NavigationDrawerContainer mListener;

    public SettingsOperation(NavigationDrawerFragment.NavigationDrawerContainer listener) {
        mListener = listener;
    }

    @Override
    public void execute() {
        mListener.onSettingsSectionRequested();
    }
}
