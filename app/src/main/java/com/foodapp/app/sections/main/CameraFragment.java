package com.foodapp.app.sections.main;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.foodapp.app.R;
import com.foodapp.app.sections.editor.ShowPictureActivity;
import com.foodapp.app.sections.main.listeners.OnTakePictureRequestedListener;
import com.foodapp.app.sections.main.views.CameraPreview;
import com.foodapp.app.utils.BitmapUtils;
import com.foodapp.app.utils.DisplayMetricsUtils;
import com.foodapp.app.utils.FileUtils;

import java.io.File;

public class CameraFragment extends Fragment implements
        Camera.AutoFocusCallback,
        OnTakePictureRequestedListener {

    private static final long CAMERA_DELAY_MS = 1500;

    private FragmentContainer mFragmentContainer;
    private FrameLayout mCameraPreviewContainer;
    private LinearLayout mCameraLoading;
    private Camera mCamera;
    private CameraPreview mPreview;
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            String appName = getActivity().getString(R.string.app_name);
            File pictureFile = FileUtils.getOutputMediaFile(appName, FileUtils.FILE_TYPE_IMAGE);
            if (pictureFile == null) {
                return;
            }

            if (BitmapUtils.getPicFilePathAndCropSquared(pictureFile, data)) {
                Intent showPictureIntent = new Intent(getActivity(), ShowPictureActivity.class);
                showPictureIntent.putExtra("path", pictureFile.getPath());
                startActivity(showPictureIntent);
            }
        }
    };

    //  A safe way to get an instance of the Camera object.
    private static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e) {
            // TODO: Camera is not available (in use or does not exist), notify user
        }
        return c;
    }

    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
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
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        mCameraPreviewContainer = (FrameLayout) view.findViewById(R.id.fl_fragment_camera_preview_container);
        mCameraLoading = (LinearLayout) view.findViewById(R.id.ll_fragment_camera_loading);

        setUpCameraPreviewLayout();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCameraPreviewContainer.setVisibility(View.GONE);
        mCameraLoading.setVisibility(View.VISIBLE);
        mCamera = getCameraInstance();
        mPreview = new CameraPreview(getActivity(), mCamera);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                mCameraLoading.setVisibility(View.GONE);
                mCameraPreviewContainer.setVisibility(View.VISIBLE);
                mCameraPreviewContainer.addView(mPreview);
            }
        }, CAMERA_DELAY_MS);

        mFragmentContainer.addTakePictureRequestedListener(this);
        mFragmentContainer.notifyCameraIsShowing();
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseCamera();
        mFragmentContainer.removeTakePictureRequestedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mCamera = null;
        mPicture = null;
        mCameraPreviewContainer = null;
        mCameraLoading = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mFragmentContainer = null;
    }

    private void setUpCameraPreviewLayout() {
        // Set width and height of the camera programmatically
        int cameraPreviewWidthHeight = DisplayMetricsUtils.getDisplayWidth(getActivity());
        int cameraPreviewMarginTopBottom = getResources().getDimensionPixelSize(R.dimen.camera_preview_margin_top_bottom);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cameraPreviewWidthHeight, cameraPreviewWidthHeight);
        params.setMargins(0, cameraPreviewMarginTopBottom, 0, cameraPreviewMarginTopBottom);
        mCameraLoading.setLayoutParams(params);
        mCameraPreviewContainer.setLayoutParams(params);
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
        mCameraPreviewContainer.removeView(mPreview);
        mPreview = null;
    }

    @Override
    public void onAutoFocus(boolean b, Camera camera) {
    }

    @Override
    public void onTakePictureRequested() {
        mCamera.takePicture(null, null, mPicture);
    }

    public interface FragmentContainer {
        void addTakePictureRequestedListener(OnTakePictureRequestedListener listener);

        void removeTakePictureRequestedListener(OnTakePictureRequestedListener listener);

        void notifyCameraIsShowing();
    }
}
