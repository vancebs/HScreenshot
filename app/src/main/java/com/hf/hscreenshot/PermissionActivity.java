package com.hf.hscreenshot;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

/**
 * Base activity for permission
 */
public abstract class PermissionActivity extends AppCompatActivity {
    private void checkPermission() {
        boolean needRequest = false;
        String[] permissions = getPermissionList();

        // check permissions
        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permission)) {
                needRequest = true;
                break;
            }
        }

        // request permission
        if (needRequest) {
            ActivityCompat.requestPermissions(this, permissions, 0);
        } else {
            // permission already granted
            onPermissionGranted();
        }
    }

    public abstract String[] getPermissionList();

    public abstract void onPermissionGranted();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int result : grantResults) {
            if (PackageManager.PERMISSION_GRANTED != result) {
                // finish if permission is not granted
                finish();
                return; // unnecessary to check more
            }
        }

        onPermissionGranted();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // check permission
        checkPermission();
    }
}
