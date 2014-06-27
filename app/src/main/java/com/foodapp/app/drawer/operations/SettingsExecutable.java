package com.foodapp.app.drawer.operations;

import com.foodapp.app.drawer.NavigationDrawerFragment;

public class SettingsExecutable implements Executable {

    private NavigationDrawerFragment.NavigationDrawerContainer mListener;

    public SettingsExecutable(NavigationDrawerFragment.NavigationDrawerContainer listener) {
        mListener = listener;
    }

    @Override
    public void execute() {
        mListener.onSettingsSectionRequested();
    }
}
