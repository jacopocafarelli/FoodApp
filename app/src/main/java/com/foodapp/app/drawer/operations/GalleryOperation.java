package com.foodapp.app.drawer.operations;

import com.foodapp.app.drawer.NavigationDrawerFragment;
import com.foodapp.app.sections.main.enums.DishType;

public class GalleryOperation implements Executable {

    private NavigationDrawerFragment.NavigationDrawerContainer mListener;
    private DishType mDishType;

    public GalleryOperation(NavigationDrawerFragment.NavigationDrawerContainer listener, DishType dishType) {
        mListener = listener;
        mDishType = dishType;
    }

    @Override
    public void execute() {
        mListener.onGallerySectionRequested(mDishType);
    }
}
