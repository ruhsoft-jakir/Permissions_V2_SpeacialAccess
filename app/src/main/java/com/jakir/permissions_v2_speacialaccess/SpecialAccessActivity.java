package com.jakir.permissions_v2_speacialaccess;

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

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.jakir.permissions_v2_speacialaccess.listner.MyAccessibilityService;
import com.jakir.permissions_v2_speacialaccess.listner.MyDeviceAdminReceiver;
import com.jakir.permissions_v2_speacialaccess.listner.MyNotificationListener;
import com.jakir.permissions.PermissionAccess;

public class SpecialAccessActivity extends AppCompatActivity {

    MaterialSwitch swDeviceAdmin, swPip, swAccessibility, swManageStorage, swOverlay, swBatteryOpt, swUsageAccess, swWriteSettings, swDnd, swNotificationListener, swUnknownSources, swMediaProjection, swGps_access;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_speacial_access);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setListeners();
        setSwitchToggle(swDeviceAdmin, swGps_access, swAccessibility, swManageStorage, swOverlay, swBatteryOpt, swUsageAccess, swWriteSettings, swDnd, swNotificationListener, swUnknownSources, swMediaProjection);
    }

    private void setSwitchToggle(MaterialSwitch swDeviceAdmin, MaterialSwitch swGpsAccess, MaterialSwitch swAccessibility, MaterialSwitch swManageStorage, MaterialSwitch swOverlay, MaterialSwitch swBatteryOpt, MaterialSwitch swUsageAccess, MaterialSwitch swWriteSettings, MaterialSwitch swDnd, MaterialSwitch swNotificationListener, MaterialSwitch swUnknownSources, MaterialSwitch swMediaProjection) {
        if (swDeviceAdmin != null)
            swDeviceAdmin.setChecked(PermissionAccess.isDeviceAdminEnabled(this, MyDeviceAdminReceiver.class));
        if (swGpsAccess != null)
            swGpsAccess.setChecked(PermissionAccess.isGPSAccessEnabled(this));
        if (swAccessibility != null)
            swAccessibility.setChecked(PermissionAccess.isAccessibilityServiceEnabled(this, MyAccessibilityService.class));
        if (swManageStorage != null)
            swManageStorage.setChecked(PermissionAccess.isManageStorageGranted(this));
        if (swOverlay != null) swOverlay.setChecked(PermissionAccess.isSystemAlertGranted(this));
        if (swBatteryOpt != null)
            swBatteryOpt.setChecked(PermissionAccess.isIgnoreBatteryOptimization(this));
        if (swUsageAccess != null)
            swUsageAccess.setChecked(PermissionAccess.isUsageStatsGranted(this));
        if (swWriteSettings != null)
            swWriteSettings.setChecked(PermissionAccess.isModifySettingsGranted(this));
        if (swDnd != null) swDnd.setChecked(PermissionAccess.isDontDistrubGranted(this));
        if (swNotificationListener != null)
            swNotificationListener.setChecked(PermissionAccess.isNotificationListenerEnabled(this));
        if (swUnknownSources != null)
            swUnknownSources.setChecked(PermissionAccess.isInstallUnknownAppsAllowed(this));
    }

    private void initViews() {
        swDeviceAdmin = findViewById(R.id.switch_device_admin);
        swGps_access = findViewById(R.id.gps_access);
        swAccessibility = findViewById(R.id.switch_accessibility);
        swManageStorage = findViewById(R.id.switch_manage_storage);
        swOverlay = findViewById(R.id.switch_overlay);
        swBatteryOpt = findViewById(R.id.switch_battery_opt);
        swUsageAccess = findViewById(R.id.switch_usage_access);
        swWriteSettings = findViewById(R.id.switch_write_settings);
        swDnd = findViewById(R.id.switch_dnd);
        swNotificationListener = findViewById(R.id.switch_notification_listener);
        swUnknownSources = findViewById(R.id.switch_unknown_sources);
        swMediaProjection = findViewById(R.id.switch_media_projection);
    }

    private void setListeners() {
        swGps_access.setOnClickListener(v -> {
            if (!PermissionAccess.isGPSAccessEnabled(this)) {
                PermissionAccess.requestGpsEnabledAccess(this, "", true, null, null);
            }
        });

        swDeviceAdmin.setOnClickListener(v -> {
            if (!PermissionAccess.isDeviceAdminEnabled(this, MyDeviceAdminReceiver.class)) {
                PermissionAccess.requestDeviceAdminAccess(this, "", true, null, MyDeviceAdminReceiver.class);
            }
        });

        swAccessibility.setOnClickListener(v -> {
            if (!PermissionAccess.isAccessibilityServiceEnabled(this, MyAccessibilityService.class)) {
                PermissionAccess.requestAccessibilityAccess(this, "", true, null, MyAccessibilityService.class);
            }
        });

        swManageStorage.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !PermissionAccess.isManageStorageGranted(this)) {
                PermissionAccess.requestManageStorageAccess(this, "", true, null, null);
            }
        });

        swOverlay.setOnClickListener(v -> {
            if (!PermissionAccess.isSystemAlertGranted(this)) {
                PermissionAccess.requestSystemAlertAccess(this, "", true, null, null);
            }
        });

        swBatteryOpt.setOnClickListener(v -> {
            if (!PermissionAccess.isIgnoreBatteryOptimization(this)) {
                PermissionAccess.requestBatteryOptimizationAccess(this, "", true, null, null);
            }
        });

        swUsageAccess.setOnClickListener(v -> {
            if (!PermissionAccess.isUsageStatsGranted(this)) {
                PermissionAccess.requestUsagesAccessAccess(this, "", true, null, null);
            }
        });

        swWriteSettings.setOnClickListener(v -> {
            if (!PermissionAccess.isModifySettingsGranted(this)) {
                PermissionAccess.requestModifySystemSettingsAccess(this, "", true, null, null);
            }
        });

        swDnd.setOnClickListener(v -> {
            if (!PermissionAccess.isDontDistrubGranted(this)) {
                PermissionAccess.requestDontDistrubAccessAccess(this, "", true, null, null);
            }
        });

        swNotificationListener.setOnClickListener(v -> {
            if (!PermissionAccess.isNotificationListenerEnabled(this)) {
                PermissionAccess.requestNotificationListenerAccess(this, "", true, null, MyNotificationListener.class);
            }
        });

        swUnknownSources.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !PermissionAccess.isInstallUnknownAppsAllowed(this)) {
                PermissionAccess.requestInstallUnknownAppsAccess(this, "", true, null, null);
            }
        });

        swMediaProjection.setOnClickListener(v -> {
            PermissionAccess.requestMediaProjectionAccess(this, "", true, null, null);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_CODE_DEVICE_ADMIN: {
                boolean status_Admin = PermissionAccess.isDeviceAdminEnabled(this, MyDeviceAdminReceiver.class);
                swDeviceAdmin.setChecked(status_Admin);
                Toast.makeText(this, status_Admin ? "✅ Device Admin granted!" : "❌ Device Admin not granted", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQUEST_CODE_GPS: {
                boolean gpsEnabled = resultCode == RESULT_OK;
                swGps_access.setChecked(gpsEnabled);
                Toast.makeText(this, gpsEnabled ? "✅ GPS enabled!" : "❌ GPS not enabled", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQUEST_CODE_ACCESSIBILITY: {
                boolean accessibility = PermissionAccess.isAccessibilityServiceEnabled(this, MyAccessibilityService.class);
                swAccessibility.setChecked(accessibility);
                Toast.makeText(this, accessibility ? "✅ Accessibility Service granted!" : "❌ Accessibility Service not granted", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQUEST_CODE_OVERLAY: {
                boolean overlay = PermissionAccess.isSystemAlertGranted(this);
                swOverlay.setChecked(overlay);
                Toast.makeText(this, overlay ? "✅ Overlay permission granted!" : "❌ Overlay permission not granted", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQUEST_CODE_BATTERY_OPT: {
                boolean batteryOpt = PermissionAccess.isIgnoreBatteryOptimization(this);
                swBatteryOpt.setChecked(batteryOpt);
                Toast.makeText(this, batteryOpt ? "✅ Ignore Battery Optimization granted!" : "❌ Ignore Battery Optimization not granted", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQUEST_CODE_USAGE_ACCESS: {
                boolean usageAccess = PermissionAccess.isUsageStatsGranted(this);
                swUsageAccess.setChecked(usageAccess);
                Toast.makeText(this, usageAccess ? "✅ Usage Access granted!" : "❌ Usage Access not granted", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQUEST_CODE_MODIFY_SYSTEM_SETTINGS: {
                boolean modifySettings = PermissionAccess.isModifySettingsGranted(this);
                swWriteSettings.setChecked(modifySettings);
                Toast.makeText(this, modifySettings ? "✅ Modify System Settings granted!" : "❌ Modify System Settings not granted", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQUEST_CODE_DONT_DISTURB: {
                boolean dnd = PermissionAccess.isDontDistrubGranted(this);
                swDnd.setChecked(dnd);
                Toast.makeText(this, dnd ? "✅ Do Not Disturb permission granted!" : "❌ Do Not Disturb permission not granted", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQUEST_CODE_INSTALL_UNKNOWN_APPS: {
                boolean unknownApps = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && PermissionAccess.isInstallUnknownAppsAllowed(this);
                swUnknownSources.setChecked(unknownApps);
                Toast.makeText(this, unknownApps ? "✅ Install Unknown Apps permission granted!" : "❌ Install Unknown Apps permission not granted", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQUEST_CODE_MANAGESTORAGE: {
                boolean fileStorage = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && PermissionAccess.isManageStorageGranted(this);
                swManageStorage.setChecked(fileStorage);
                Toast.makeText(this, fileStorage ? "✅ File storage access granted!" : "❌ File storage access not granted", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQUEST_CODE_NOTIFICATION_LISTENER: {
                boolean notificationListener = PermissionAccess.isNotificationListenerEnabled(this);
                swNotificationListener.setChecked(notificationListener);
                Toast.makeText(this, notificationListener ? "✅ Notification Listener granted!" : "❌ Notification Listener not granted", Toast.LENGTH_SHORT).show();
                break;
            }

            case REQUEST_CODE_MEDIA_PROJECTION: {
                boolean screenCapture = resultCode == RESULT_OK && data != null;
                swMediaProjection.setChecked(screenCapture);
                Toast.makeText(this, screenCapture ? "✅ Screen capture permission granted!" : "❌ Screen capture permission denied", Toast.LENGTH_SHORT).show();
                break;
            }
        }

    }
}
