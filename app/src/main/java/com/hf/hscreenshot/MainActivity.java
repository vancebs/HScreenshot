package com.hf.hscreenshot;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hf.imagecompare.ImageCompare;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends PermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageCompare ic = new ImageCompare();

        Button buttonTest = (Button) findViewById(R.id.btn_test);
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageCompare.Mat mat1 = ImageCompare.Mat.fromFile("/sdcard/1.png");
                ImageCompare.Mat mat2 = ImageCompare.Mat.fromFile("/sdcard/2.png");

                int[] outDistance = new int[1];
                ImageCompare.Mat matMergedMask = ic.autoMerge(mat1, mat2, ImageCompare.DIRECTION_ANY, outDistance);
                if (outDistance[0] != ImageCompare.INVALID_DISTANCE) {
                    matMergedMask.toFile("/sdcard/3.png");
                    Toast.makeText(MainActivity.this, "distance: " + outDistance[0], Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "distance: Invalid", Toast.LENGTH_SHORT).show();
                }

                mat1.release();
                mat2.release();
//                matMerged.release();
                matMergedMask.release();
            }
        });
    }

    @Override
    public String[] getPermissionList() {
        return new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }

    @Override
    public void onPermissionGranted() {

    }
}
