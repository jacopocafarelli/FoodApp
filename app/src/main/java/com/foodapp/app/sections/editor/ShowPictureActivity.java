package com.foodapp.app.sections.editor;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.foodapp.app.R;

import java.io.File;

public class ShowPictureActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);

        Bundle extras = getIntent().getExtras();
        String imagePath;
        if (extras != null) {
            imagePath = getIntent().getExtras().getString("path");
            if (!TextUtils.isEmpty(imagePath)) {
                File imgFile = new File(imagePath);
                if (imgFile.exists()) {

                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                    ImageView myImage = (ImageView) findViewById(R.id.photo);
                    myImage.setImageBitmap(myBitmap);
                }
            } else {
                // TODO: notify user with some error message!!
            }
        }
    }
}

