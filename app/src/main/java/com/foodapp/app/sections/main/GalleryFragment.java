package com.foodapp.app.sections.main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foodapp.app.R;
import com.foodapp.app.utils.DisplayMetricsUtils;

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

        Context context = getActivity();
        TextView screenWidth = (TextView) view.findViewById(R.id.tv_screen_width);
        screenWidth.setText("La larghezza dello schermo è: " + DisplayMetricsUtils.getDisplayWidth(context) + " px");
        TextView screenHeight = (TextView) view.findViewById(R.id.tv_screen_height);
        screenHeight.setText("L' altezza dello schermo è: " + DisplayMetricsUtils.getDisplayHeight(context) + " px");
        TextView statusBarHeight = (TextView) view.findViewById(R.id.tv_status_bar_height);
        statusBarHeight.setText("La status bar misura: " + DisplayMetricsUtils.getStatusBarHeight(context) + " px");
        TextView actionBarHeight = (TextView) view.findViewById(R.id.tv_action_bar_height);
        actionBarHeight.setText("L' action bar misura: " + DisplayMetricsUtils.getActionBarHeight(context) + " px");
        TextView navigationBar = (TextView) view.findViewById(R.id.tv_navigation_bar_height);
        int navigationBarHeight = DisplayMetricsUtils.getNavigationBarHeight(context);
        if (navigationBarHeight == 0) {
            navigationBar.setText("Il device non ha la navigation bar");
        } else {
            navigationBar.setText("La navigation bar misura: " + navigationBarHeight + " px");
        }
        TextView phoneOrTablet = (TextView) view.findViewById(R.id.tv_phone_or_tablet);
        if (DisplayMetricsUtils.isPhone(context)) {
            phoneOrTablet.setText("Il device è un telefono");
        } else {
            phoneOrTablet.setText("Il device è un tablet");
        }
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
