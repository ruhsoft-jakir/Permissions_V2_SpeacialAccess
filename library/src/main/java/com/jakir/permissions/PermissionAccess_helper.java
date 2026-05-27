package com.jakir.permissions;

import java.util.HashMap;

//
// Created by JAKIR HOSSAIN on 8/16/2025.
//
public class PermissionAccess_helper {
    // 🔢 All request codes
    public static final int REQUEST_CODE_GPS = 111101, REQUEST_CODE_DEVICE_ADMIN = 111102, REQUEST_CODE_ACCESSIBILITY = 111103, REQUEST_CODE_OVERLAY = 111104, REQUEST_CODE_BATTERY_OPT = 111105, REQUEST_CODE_USAGE_ACCESS = 111106, REQUEST_CODE_MODIFY_SYSTEM_SETTINGS = 111107, REQUEST_CODE_DONT_DISTURB = 111108, REQUEST_CODE_INSTALL_UNKNOWN_APPS = 1111010, REQUEST_CODE_NOTIFICATION_LISTENER = 1111011, REQUEST_CODE_MEDIA_PROJECTION = 1111012, REQUEST_CODE_MANAGESTORAGE = 1111013;

    private static final HashMap<Integer, String> specialPermissionNameMap = new HashMap<>();
    private static final HashMap<Integer, Integer> specialPermissionAnimMap = new HashMap<>();
    private static final HashMap<Integer, String> specialPermissionMessageMap = new HashMap<>();

    static {
        // Fill with request codes and their names
        specialPermissionNameMap.put(REQUEST_CODE_GPS, "GPS / Location");
        specialPermissionNameMap.put(REQUEST_CODE_DEVICE_ADMIN, "Device Admin");
        specialPermissionNameMap.put(REQUEST_CODE_ACCESSIBILITY, "Accessibility Service");
        specialPermissionNameMap.put(REQUEST_CODE_OVERLAY, "Display over other apps");
        specialPermissionNameMap.put(REQUEST_CODE_BATTERY_OPT, "Ignore Battery Optimization");
        specialPermissionNameMap.put(REQUEST_CODE_USAGE_ACCESS, "Usage Access");
        specialPermissionNameMap.put(REQUEST_CODE_MANAGESTORAGE, "All Files Access");
        specialPermissionNameMap.put(REQUEST_CODE_MODIFY_SYSTEM_SETTINGS, "Modify System Settings");
        specialPermissionNameMap.put(REQUEST_CODE_DONT_DISTURB, "Do Not Disturb Access");
        specialPermissionNameMap.put(REQUEST_CODE_INSTALL_UNKNOWN_APPS, "Install Unknown Apps");
        specialPermissionNameMap.put(REQUEST_CODE_NOTIFICATION_LISTENER, "Device & App Notification");
        specialPermissionNameMap.put(REQUEST_CODE_MEDIA_PROJECTION, "Media Projection (Screen Capture)");
    }

    static {
        // Example: Using Lottie/raw animation or drawable icons
        specialPermissionAnimMap.put(REQUEST_CODE_GPS, R.raw.gps01);
        specialPermissionAnimMap.put(REQUEST_CODE_DEVICE_ADMIN, R.raw.websites_securety);
        specialPermissionAnimMap.put(REQUEST_CODE_ACCESSIBILITY, R.raw.admin01);
        specialPermissionAnimMap.put(REQUEST_CODE_OVERLAY, R.raw.over_other_apps03);
        specialPermissionAnimMap.put(REQUEST_CODE_MANAGESTORAGE, R.raw.folder01);
        specialPermissionAnimMap.put(REQUEST_CODE_BATTERY_OPT, R.raw.clock_loop);
        specialPermissionAnimMap.put(REQUEST_CODE_USAGE_ACCESS, R.raw.admin02);
        specialPermissionAnimMap.put(REQUEST_CODE_MODIFY_SYSTEM_SETTINGS, R.raw.settings_robot);
        specialPermissionAnimMap.put(REQUEST_CODE_DONT_DISTURB, R.raw.mouth_zip);
        specialPermissionAnimMap.put(REQUEST_CODE_INSTALL_UNKNOWN_APPS, R.raw.android);
        specialPermissionAnimMap.put(REQUEST_CODE_NOTIFICATION_LISTENER, R.raw.notification04);
        specialPermissionAnimMap.put(REQUEST_CODE_MEDIA_PROJECTION, R.raw.window01);
    }

    static {
        specialPermissionMessageMap.put(REQUEST_CODE_GPS, "This app needs Location (GPS) access to provide location-based features.");
        specialPermissionMessageMap.put(REQUEST_CODE_DEVICE_ADMIN, "Device Admin permission is required for advanced security features.");
        specialPermissionMessageMap.put(REQUEST_CODE_ACCESSIBILITY, "Accessibility Service is required to enhance app functionality.");
        specialPermissionMessageMap.put(REQUEST_CODE_OVERLAY, "Overlay permission is needed to display content over other apps.");
        specialPermissionMessageMap.put(REQUEST_CODE_BATTERY_OPT, "Allow Ignore Battery Optimization for better background performance.");
        specialPermissionMessageMap.put(REQUEST_CODE_MANAGESTORAGE, "Files Storage Access is needed to read and manage all files on your device.");
        specialPermissionMessageMap.put(REQUEST_CODE_USAGE_ACCESS, "Usage Access is required to monitor app usage statistics.");
        specialPermissionMessageMap.put(REQUEST_CODE_MODIFY_SYSTEM_SETTINGS, "System Settings modification is required for customizing device behavior.");
        specialPermissionMessageMap.put(REQUEST_CODE_DONT_DISTURB, "Do Not Disturb access is needed to control notification settings.");
        specialPermissionMessageMap.put(REQUEST_CODE_INSTALL_UNKNOWN_APPS, "This permission allows installing apps from unknown sources.");
        specialPermissionMessageMap.put(REQUEST_CODE_NOTIFICATION_LISTENER, "App Notification access is required to read and manage notifications.");
        specialPermissionMessageMap.put(REQUEST_CODE_MEDIA_PROJECTION, "Media Projection is required to capture or record your screen.");
    }


    // Get name by code
    public static String getAccessPermissionName(int code) {
        String name = specialPermissionNameMap.get(code);
        return (name != null) ? name : "Unknown Permission";
    }

    // Method to get animation by code
    public static int getAccessPermissionAnim(int code) {
        Integer animRes = specialPermissionAnimMap.get(code);
        return (animRes != null) ? animRes : R.raw.permissionrequried; // default fallback
    }

    // Method to get message by code
    public static String getAccessPermissionMessage(int code) {
        String msg = specialPermissionMessageMap.get(code);
        return (msg != null) ? msg : "This permission is required for app functionality.";
    }
}
