package com.jakir.permissions;


import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_ACCESSIBILITY;
import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_BATTERY_OPT;
import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_DEVICE_ADMIN;
import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_DONT_DISTURB;
import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_GPS;
import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_INSTALL_UNKNOWN_APPS;
import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_MANAGESTORAGE;
import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_MEDIA_PROJECTION;
import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_MODIFY_SYSTEM_SETTINGS;
import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_NOTIFICATION_LISTENER;
import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_OVERLAY;
import static com.jakir.permissions.PermissionAccess_helper.REQUEST_CODE_USAGE_ACCESS;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.os.Process;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.jakir.permissions.dialogs.Bottomsheet_dialog_Access;

public class PermissionAccess {

    public static boolean isDeviceAdminEnabled(Context context, Class<?> adminClass) {
        DevicePolicyManager dpm = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName adminComponent = new ComponentName(context, adminClass);
        return dpm.isAdminActive(adminComponent);
    }

    public static boolean isPipAllowed(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE);
        }
        return false;
    }

    public static boolean isPipAllowed0(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                mode = appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_PICTURE_IN_PICTURE, Process.myUid(), context.getPackageName());
            }
            return mode == AppOpsManager.MODE_ALLOWED;
        }
        return false;
    }

    public static boolean isAccessibilityServiceEnabled(Context context, Class<?> serviceClass) {
        String enabledServices = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
        return enabledServices != null && enabledServices.contains(new ComponentName(context, serviceClass).flattenToString());
    }

    public static boolean isManageStorageGranted(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }
        return true;
    }

    public static boolean isSystemAlertGranted(Context context) {
        return Settings.canDrawOverlays(context);
    }

    public static boolean isIgnoreBatteryOptimization(Context context) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return pm.isIgnoringBatteryOptimizations(context.getPackageName());
    }

    public static boolean isUsageStatsGranted(Context context) {
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    public static boolean isModifySettingsGranted(Context context) {
        return Settings.System.canWrite(context);
    }

    public static boolean isDontDistrubGranted(Context context) {
        android.app.NotificationManager nm = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        return nm.isNotificationPolicyAccessGranted();
    }

    public static boolean isNotificationListenerEnabled(Context context) {
        String flat = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        return flat != null && flat.contains(context.getPackageName());
    }

    public static boolean isInstallUnknownAppsAllowed(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return context.getPackageManager().canRequestPackageInstalls();
        }
        return true;
    }

    public static boolean isGPSAccessEnabled(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public static void requestDeviceAdmin(Context context, Class<?> DeviceAdminClass) {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, new ComponentName(context, DeviceAdminClass));
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "We need context permission to enable advanced security features.");
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_DEVICE_ADMIN);
    }

    public static void requestAccessibility(Context context) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_ACCESSIBILITY);
    }

    public static void requestSystemAlert(Context context) {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + context.getPackageName()));
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_OVERLAY);
        } catch (Exception e) {
            // fallback: open general overlay settings
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_OVERLAY);
        }
    }

    @SuppressLint("BatteryLife")
    public static void requestBatteryOptimization(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_BATTERY_OPT);
    }

    public static void requestUsages(Context context) {
        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS, Uri.parse("package:" + context.getPackageName()));
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_USAGE_ACCESS);
    }

    public static void requestModifySystemSettings(Context context) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + context.getPackageName()));
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_MODIFY_SYSTEM_SETTINGS);
    }

    public static void requestDontDistrub(Context context) {
        Intent intent = new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_DONT_DISTURB);
    }

    public static void requestManageStorage(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", context.getPackageName())));
                    ((Activity) context).startActivityIfNeeded(intent, REQUEST_CODE_MANAGESTORAGE);
                } catch (Exception exception) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    ((Activity) context).startActivityIfNeeded(intent, REQUEST_CODE_MANAGESTORAGE);
                }
            }
        }
    }

    public static void requestNotificationListener(Context context, Class<?> NotificationClass) {
        try {
            // Create intent for notification listener settings
            Intent intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            String componentName = context.getPackageName() + "/" + NotificationClass.getName();
            Bundle bundle = new Bundle();
            bundle.putString(":settings:fragment_args_key", componentName);
            intent.putExtra(":settings:fragment_args_key", componentName);
            intent.putExtra(":settings:show_fragment_args", bundle);
            ((Activity) context).startActivityForResult(intent, REQUEST_CODE_NOTIFICATION_LISTENER);

        } catch (Exception e) {
            // Fallback: open normal notification listener settings page
            Intent fallbackIntent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            ((Activity) context).startActivityForResult(fallbackIntent, REQUEST_CODE_NOTIFICATION_LISTENER);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void requestInstallUnknownApps(Context context) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + context.getPackageName()));
        ((Activity) context).startActivityForResult(intent, REQUEST_CODE_INSTALL_UNKNOWN_APPS);
    }

    public static void requestMediaProjection(Context context) {
        MediaProjectionManager mpm = (MediaProjectionManager) context.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        ((Activity) context).startActivityForResult(mpm.createScreenCaptureIntent(), REQUEST_CODE_MEDIA_PROJECTION);

    }

    public static void requestGpsEnabled(Context context) {
        LocationRequest locationRequest;
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(2000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest locationSettingsRequest = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build();
        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> locationSettingsResponseTask = settingsClient.checkLocationSettings(locationSettingsRequest);
        locationSettingsResponseTask.addOnSuccessListener(locationSettingsRequest1 -> {
            //settings of device are satisfied and can start location update
        });

        locationSettingsResponseTask.addOnFailureListener(e -> {

            if (e instanceof ResolvableApiException) {
                ResolvableApiException apiException = (ResolvableApiException) e;
                try {
                    apiException.startResolutionForResult((Activity) context, REQUEST_CODE_GPS);
                } catch (IntentSender.SendIntentException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public static void requestGpsEnabledAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_GPS, msg, showDialog, image, classService);
    }

    public static void requestDeviceAdminAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_DEVICE_ADMIN, msg, showDialog, image, classService);
    }

    public static void requestAccessibilityAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_ACCESSIBILITY, msg, showDialog, image, classService);
    }

    public static void requestSystemAlertAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_OVERLAY, msg, showDialog, image, classService);
    }

    public static void requestBatteryOptimizationAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_BATTERY_OPT, msg, showDialog, image, classService);
    }

    public static void requestUsagesAccessAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_USAGE_ACCESS, msg, showDialog, image, classService);
    }

    public static void requestManageStorageAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_MANAGESTORAGE, msg, showDialog, image, classService);
    }

    public static void requestModifySystemSettingsAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_MODIFY_SYSTEM_SETTINGS, msg, showDialog, image, classService);
    }

    public static void requestDontDistrubAccessAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_DONT_DISTURB, msg, showDialog, image, classService);
    }

    public static void requestNotificationListenerAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_NOTIFICATION_LISTENER, msg, showDialog, image, classService);
    }

    public static void requestInstallUnknownAppsAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_INSTALL_UNKNOWN_APPS, msg, showDialog, image, classService);
    }

    public static void requestMediaProjectionAccess(Context context, String msg, boolean showDialog, Drawable image, Class<?> classService) {
        requestAccessPermissions(context, REQUEST_CODE_MEDIA_PROJECTION, msg, showDialog, image, classService);
    }

    public static void requestAccessPermissions(Context context, int requestCode, String message, boolean showDialog, Drawable image, Class<?> classService) {
        Bottomsheet_dialog_Access dialog = new Bottomsheet_dialog_Access(context);
        if (showDialog) {
            dialog.show(requestCode, message, image, classService);
        } else {
            dialog.Notshow(requestCode, classService);
        }
    }

}
