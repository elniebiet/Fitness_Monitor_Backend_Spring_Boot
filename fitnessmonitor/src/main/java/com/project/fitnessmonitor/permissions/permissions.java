package com.project.fitnessmonitor.permissions;

public class permissions {
    private int permissionGranted = 0;
    private String deviceRequested = "";
    private String requestedDevice = "";

    public permissions(int permissionGranted, String deviceRequested, String requestedDevice) {
        this.permissionGranted = permissionGranted;
        this.deviceRequested = deviceRequested;
        this.requestedDevice = requestedDevice;
    }

    public int getPermissionGranted() {
        return permissionGranted;
    }

    public String getDeviceRequested() {
        return deviceRequested;
    }

    public String getRequestedDevice() {
        return requestedDevice;
    }
}
