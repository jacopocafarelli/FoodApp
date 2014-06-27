package com.foodapp.app.drawer.operations;

import com.foodapp.app.drawer.NavigationDrawerFragment;

public class FeedbackOperation implements Executable {

    private NavigationDrawerFragment.NavigationDrawerContainer mListener;
    private String mReason;
    private String mMessage;

    public FeedbackOperation(NavigationDrawerFragment.NavigationDrawerContainer listener,
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
