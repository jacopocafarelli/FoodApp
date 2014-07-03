package com.foodapp.app.utils;

import android.os.Environment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    public static final int FILE_TYPE_IMAGE = 0;

    private static final String IMAGE_FILE_PREFIX = "IMG_";
    private static final String IMAGE_FILE_JPG_EXTENSION = ".jpg";
    private static final String IMAGE_FILE_DATE_FORMAT = "yyyyMMdd_HHmmss";

    // Create a File for saving an image
    public static File getOutputMediaFile(String appName, int type) {
        // TODO: To be safe, you should check that the SDCard is mounted using
        // TODO: Environment.getExternalStorageState() before doing this.

        // This location works best if you want the created images to be shared between applications
        // and persist after your app has been uninstalled.
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), appName);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                LogUtils.logDebug("Failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat(IMAGE_FILE_DATE_FORMAT).format(new Date());
        File mediaFile = null;
        if (type == FILE_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + IMAGE_FILE_PREFIX + timeStamp + IMAGE_FILE_JPG_EXTENSION);
        }

        return mediaFile;
    }
}
