package com.denisroyz.ringassignment.data;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by Heralt on 13.09.2017.
 */

public class PermissionManager {
    private static final String[] PERMISSIONS = new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final String TAG = "PermissionManager";

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 1421;

    public boolean checkPermissions(Activity activity) {
        boolean isHaveAllPermissions = true;
        for(String permission: PERMISSIONS) {
            int permission_status = ActivityCompat.checkSelfPermission(activity, permission);
            if (permission_status != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, String.format("Permission(%s) is not granted.", permission));
                isHaveAllPermissions  = false;
            } else {
                Log.i(TAG, String.format("Permission(%s) granted", permission));
            }
        }
        return isHaveAllPermissions;
    }

    public boolean validatePermissionResult(int requestCode, int[] grantResults) {
        if (requestCode!=REQUEST_CODE_ASK_PERMISSIONS) return false;
        for (int grantResult : grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void requestPermissions(Activity activity){
        ActivityCompat.requestPermissions(activity, PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
    }
}
