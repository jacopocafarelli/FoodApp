package com.foodapp.app.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {

    public static boolean getPicFilePathAndCropSquared(File picture, byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        int bitmapWidth = bitmap.getWidth();
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmapWidth, bitmapWidth);

        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 90, blob);
        byte[] bitmapData = blob.toByteArray();

        try {
            FileOutputStream fos = new FileOutputStream(picture);
            fos.write(bitmapData);
            fos.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
