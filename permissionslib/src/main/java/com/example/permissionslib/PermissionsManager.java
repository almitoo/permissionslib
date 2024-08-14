package com.example.permissionslib;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PermissionsManager {

    private static PermissionsManager instance;
    private final Set<PermissionChangeListener> listeners = new HashSet<>();


    private PermissionsManager() {}

    public static PermissionsManager getInstance() {
        if (instance == null) {
            synchronized (PermissionsManager.class) {
                if (instance == null) {
                    instance = new PermissionsManager();
                }
            }
        }
        return instance;
    }

    public boolean hasPermissions(Context context, String[] permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public void requestPermissions(Activity activity, String[] permissions, int requestCode) {
        for (String permission : permissions) {
            if (!isPermissionGranted(activity,permission)) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        }
    }

    public void notifyPermissionChanged() {
        for (PermissionChangeListener listener : listeners) {
            listener.onPermissionChanged();
        }
    }

    public void registerListener(PermissionChangeListener listener) {
        listeners.add(listener);
    }

    public void unregisterListener(PermissionChangeListener listener) {
        listeners.remove(listener);
    }

    public boolean isPermissionGranted(Context context, String permission) {
        if (context != null && permission != null) {
            boolean granted = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
            return granted;
        }
        return false;
    }

    public  void requestPermissionIfNotGranted(Activity activity, boolean shouldRequest, String permission, int requestCode) {
        if (!isPermissionGranted(activity, permission)) {
            if (shouldRequest) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        }
    }

    public String[] getDeclaredPermissions(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_PERMISSIONS);
            return packageInfo.requestedPermissions != null ? packageInfo.requestedPermissions : new String[0];
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public interface PermissionChangeListener {
        void onPermissionChanged();
    }
}

