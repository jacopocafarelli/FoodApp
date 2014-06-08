package com.foodapp.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TakePictureActivity extends Activity implements SurfaceHolder.Callback {
    // a variable to store a reference to the Image View at the main.xml file
// private ImageView iv_image;
// a variable to store a reference to the Surface View at the main.xml file
    private SurfaceView sv;

    // a bitmap to display the captured image
    private Bitmap bmp;
    FileOutputStream fo;

    // Camera variables
// a surface holder
    private SurfaceHolder sHolder;
    // a variable to control the camera
    private Camera mCamera;
    // the camera parameters
    private Camera.Parameters parameters;
    private String FLASH_MODE ;
    private boolean isFrontCamRequest = false;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // sets what code should be executed after the picture is taken
    Camera.PictureCallback mCall = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            finish();

        }
    };

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "FoodTastic");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("FoodTastic", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +  "IMG_"+ timeStamp + ".jpg");
        }else {
            return null;
        }

        return mediaFile;
    }


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_surface_holder);

        // check if this device has a camera
        if (checkCameraHardware(getApplicationContext())) {
            // get the Image View at the main.xml file
            // iv_image = (ImageView) findViewById(R.id.imageView);

            // get the Surface View at the main.xml file
            Bundle extras = getIntent().getExtras();
            String flash_mode = "";
            boolean front_cam_req = true;
            if (extras != null) {
                flash_mode = extras.getString("FLASH");
                front_cam_req = extras.getBoolean("Front_Request");
            }
            FLASH_MODE = flash_mode;
            isFrontCamRequest = front_cam_req;

            sv = (SurfaceView) findViewById(R.id.camera_preview);

            // Get a surface
            sHolder = sv.getHolder();

            // add the callback interface methods defined below as the Surface View callbacks
            sHolder.addCallback(this);

            // tells Android that this surface will have its data constantly replaced
            sHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        } else {
            // display in long period of time
            Toast.makeText(getApplicationContext(), "Your Device dosen't have a Camera !", Toast.LENGTH_LONG).show();
        }

    }

    /** Check if this device has a camera */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // get camera parameters
        parameters = mCamera.getParameters();
        if (FLASH_MODE == null || FLASH_MODE.isEmpty())
        {
            FLASH_MODE = "auto";
        }
        parameters.setFlashMode(FLASH_MODE);
//        parameters.setPreviewSize(sv.getLayoutParams().width, sv.getLayoutParams().height);

        // set camera parameters
        mCamera.setParameters(parameters);
        mCamera.setDisplayOrientation(90);
        mCamera.startPreview();

    }

    private void takePicture() {
        mCamera.takePicture(null, null, mCall);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, acquire the camera and tell it where
        // to draw the preview.

        mCamera = getCameraInstance();
        try {
            mCamera.setPreviewDisplay(holder);

        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
        }
    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mCamera!=null){
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);

            mCamera.release();
            mCamera = null;
        }
    }


}

