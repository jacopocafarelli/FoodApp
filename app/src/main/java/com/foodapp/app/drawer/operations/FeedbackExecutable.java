package com.foodapp.app.drawer.operations;

import com.foodapp.app.drawer.NavigationDrawerFragment;

public class FeedbackExecutable implements Executable {

    private NavigationDrawerFragment.NavigationDrawerContainer mListener;
    private String mReason;
    private String mMessage;

    public FeedbackExecutable(NavigationDrawerFragment.NavigationDrawerContainer listener,
                              String reason, String message) {
        mListener = listener;
        mReason = reason;
        mMessage = message;
    }

    @Override
    public void execute() {
        mListener.onSendFeedbackRequested(mReason, mMessage);
    }
}
