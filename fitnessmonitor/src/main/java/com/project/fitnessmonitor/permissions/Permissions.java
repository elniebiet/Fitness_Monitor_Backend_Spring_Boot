/*
 * created by Aniebiet Akpan
 * */

package com.project.fitnessmonitor.permissions;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Permissions {
    private int permissionGranted = 0;
    private String deviceRequested = "";
    private String requestingDevice = "";

    public Permissions(@JsonProperty("permissionGranted") int permissionGranted, @JsonProperty("deviceRequested") String deviceRequested, @JsonProperty("requestingDevice") String requestingDevice) {
        this.permissionGranted = permissionGranted;
        this.deviceRequested = deviceRequested;
        this.requestingDevice = requestingDevice;
    }

    @Override
    public String toString() {
        return "Permissions{" +
                "permissionGranted=" + permissionGranted +
                ", deviceRequested='" + deviceRequested + '\'' +
                ", requestingDevice='" + requestingDevice + '\'' +
                '}';
    }

    public int getPermissionGranted() {
        return permissionGranted;
    }

    public String getDeviceRequested() {
        return deviceRequested;
    }

    public String getRequestingDevice() {
        return requestingDevice;
    }
}
