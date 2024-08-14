package com.example.permissionslib;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtils {

    public static void handlePermissionRequest(Context context, String permission,int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission)) {
            showPermissionRationaleDialog(context, permission,requestCode);
        } else {
            openAppSettings(context);
        }
    }

    private static void showPermissionRationaleDialog(Context context, String permission,int requestCode) {
        new AlertDialog.Builder(context)
                .setTitle("Permission Required")
                .setMessage("This permission is required for the app to function properly. Please grant the permission in app settings.")
                .setPositiveButton("OK", (dialog, which) -> PermissionsManager.getInstance().requestPermissionIfNotGranted((Activity) context, true, permission, requestCode))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private static void openAppSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", context.getPackageName(), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}

