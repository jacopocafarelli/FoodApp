package com.foodapp.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.File;

public class ShowPictureActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);

        String imagePath = getIntent().getExtras().getString("path");
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView myImage = (ImageView) findViewById(R.id.photo);
            myImage.setImageBitmap(myBitmap);
        }
    }
}

