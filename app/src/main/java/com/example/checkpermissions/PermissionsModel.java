package com.example.checkpermissions;

public class PermissionsModel {
    String permissionName;
    int image;
    boolean isGranted;

    public PermissionsModel(String permissionName, int image) {
        this.permissionName = permissionName;
        this.image = image;
        this.isGranted = false;
    }


    public String getPermissionName() {
        return permissionName;
    }

    public int getImage() {
        return image;
    }
    public boolean isGranted() {
        return isGranted;
    }

    public void setGranted(boolean granted) {
        isGranted = granted;
    }
}
