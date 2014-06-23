package com.foodapp.app.sections.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.foodapp.app.R;

public class GalleryFragment extends Fragment {

    private FragmentContainer mFragmentContainer;

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Fragment parentFragment = getParentFragment();
        if (parentFragment == null) {
            mFragmentContainer = (FragmentContainer) activity;
        } else {
            mFragmentContainer = (FragmentContainer) parentFragment;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentContainer = null;
    }

    public interface FragmentContainer {

    }
}
