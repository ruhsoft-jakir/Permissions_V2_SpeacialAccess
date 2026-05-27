package com.jakir.permissions_v2_speacialaccess;

import android.content.Context;
import android.content.Intent;
import android.media.projection.MediaProjectionManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PermissionPreference;
import androidx.preference.PreferenceFragmentCompat;

import com.jakir.permissions_v2_speacialaccess.listner.MyAccessibilityService;
import com.jakir.permissions_v2_speacialaccess.listner.MyDeviceAdminReceiver;
import com.jakir.permissions_v2_speacialaccess.listner.MyNotificationListener;
import com.jakir.permissions.PermissionAccess;

public class SettingsSpecialAccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(androidx.preference.R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(androidx.preference.R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Toolbar toolbar = findViewById(androidx.preference.R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Settings");
            actionBar.setHomeAsUpIndicator(androidx.preference.R.drawable.round_arrow_back_ios_24);
        }

        // Load Settings Fragment
        getSupportFragmentManager().beginTransaction().replace(androidx.preference.R.id.container, new SettingsFragment()).commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private PermissionPreference switch_device_admin, gps_access, switch_accessibility, switch_manage_storage, switch_overlay, switch_battery_opt, switch_usage_access, switch_write_settings, switch_dnd, switch_notification_listener, switch_unknown_sources, switch_media_projection;
        // Permission buttons

        // Launchers
        private ActivityResultLauncher<Intent> gpsLauncher;
        private ActivityResultLauncher<Intent> mediaProjectionLauncher;


        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);

            gps_access = findPreference("gps_access");
            if (gps_access != null) {
                gps_access.setAllowed(PermissionAccess.isGPSAccessEnabled(getContext()));
                gps_access.setAcceptedText("Permission Granted");
                gps_access.setRequestText("Please Allow");
                gps_access.setOnPreferenceClickListener(preference -> {
                    if (!PermissionAccess.isGPSAccessEnabled(getContext())) {
                        PermissionAccess.requestGpsEnabledAccess(getContext(), "", true, null, null);
                    }
                    return true;
                });
            }
            switch_device_admin = findPreference("switch_device_admin");
            if (switch_device_admin != null) {
                switch_device_admin.setAllowed(PermissionAccess.isDeviceAdminEnabled(getContext(), MyDeviceAdminReceiver.class));
                switch_device_admin.setAcceptedText("Permission Granted");
                switch_device_admin.setRequestText("Please Allow");
                switch_device_admin.setOnPreferenceClickListener(preference -> {
                    if (!PermissionAccess.isDeviceAdminEnabled(getContext(), MyDeviceAdminReceiver.class)) {
                        PermissionAccess.requestDeviceAdminAccess(getContext(), "", true, null, MyDeviceAdminReceiver.class);
                    }
                    return true;
                });
            }

            switch_accessibility = findPreference("switch_accessibility");
            if (switch_accessibility != null) {
                switch_accessibility.setAllowed(PermissionAccess.isAccessibilityServiceEnabled(getContext(), MyAccessibilityService.class));
                switch_accessibility.setAcceptedText("Allowed");
                switch_accessibility.setRequestText("Please Allow");
                switch_accessibility.setOnPreferenceClickListener(preference -> {
                    if (!PermissionAccess.isAccessibilityServiceEnabled(getContext(), MyAccessibilityService.class)) {
                        PermissionAccess.requestAccessibilityAccess(getContext(), "", true, null, MyAccessibilityService.class);
                    }
                    return true;
                });
            }
            switch_manage_storage = findPreference("switch_manage_storage");
            if (switch_manage_storage != null) {
                switch_manage_storage.setAllowed(PermissionAccess.isManageStorageGranted(getContext()));
                switch_manage_storage.setAcceptedText("Permission Granted");
                switch_manage_storage.setRequestText("Please Allow");
                switch_manage_storage.setOnPreferenceClickListener(preference -> {
                    if (!PermissionAccess.isManageStorageGranted(getContext()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        PermissionAccess.requestManageStorageAccess(getContext(), "", true, null, null);
                        }
                    return true;
                });
            }
            switch_overlay = findPreference("switch_overlay");
            if (switch_overlay != null) {
                switch_overlay.setAllowed(PermissionAccess.isSystemAlertGranted(getContext()));
                switch_overlay.setAcceptedText("Permission Granted");
                switch_overlay.setRequestText("Please Allow");
                switch_overlay.setOnPreferenceClickListener(preference -> {
                    if (!PermissionAccess.isSystemAlertGranted(getContext())) {
                        PermissionAccess.requestSystemAlertAccess(getContext(), "", true, null, null);
                    }
                    return true;
                });
            }
            switch_battery_opt = findPreference("switch_battery_opt");
            if (switch_battery_opt != null) {
                switch_battery_opt.setAllowed(PermissionAccess.isIgnoreBatteryOptimization(getContext()));
                switch_battery_opt.setAcceptedText("Permission Granted");
                switch_battery_opt.setRequestText("Please Allow");
                switch_battery_opt.setOnPreferenceClickListener(preference -> {
                    if (!PermissionAccess.isIgnoreBatteryOptimization(getContext())) {
                        PermissionAccess.requestBatteryOptimizationAccess(getContext(), "", true, null, null);
                    }
                    return true;
                });
            }
            switch_usage_access = findPreference("switch_usage_access");
            if (switch_usage_access != null) {
                switch_usage_access.setAllowed(PermissionAccess.isUsageStatsGranted(getContext()));
                switch_usage_access.setAcceptedText("Permission Granted");
                switch_usage_access.setRequestText("Please Allow");
                switch_usage_access.setOnPreferenceClickListener(preference -> {
                    if (!PermissionAccess.isUsageStatsGranted(getContext())) {
                        PermissionAccess.requestUsagesAccessAccess(getContext(), "", true, null, null);
                    }
                    return true;
                });
            }
            switch_write_settings = findPreference("switch_write_settings");
            if (switch_write_settings != null) {
                switch_write_settings.setAllowed(PermissionAccess.isModifySettingsGranted(getContext()));
                switch_write_settings.setAcceptedText("Permission Granted");
                switch_write_settings.setRequestText("Please Allow");
                switch_write_settings.setOnPreferenceClickListener(preference -> {
                    if (!PermissionAccess.isModifySettingsGranted(getContext())) {
                        PermissionAccess.requestModifySystemSettingsAccess(getContext(), "", true, null, null);
                    }
                    return true;
                });
            }
            switch_dnd = findPreference("switch_dnd");
            if (switch_dnd != null) {
                switch_dnd.setAllowed(PermissionAccess.isDontDistrubGranted(getContext()));
                switch_dnd.setAcceptedText("Permission Granted");
                switch_dnd.setRequestText("Please Allow");
                switch_dnd.setOnPreferenceClickListener(preference -> {
                    if (!PermissionAccess.isDontDistrubGranted(getContext())) {
                        PermissionAccess.requestDontDistrubAccessAccess(getContext(), "", true, null, null);
                    }
                    return true;
                });
            }
            switch_notification_listener = findPreference("switch_notification_listener");
            if (switch_notification_listener != null) {
                switch_notification_listener.setAllowed(PermissionAccess.isNotificationListenerEnabled(getContext()));
                switch_notification_listener.setAcceptedText("Permission Granted");
                switch_notification_listener.setRequestText("Please Allow");
                switch_notification_listener.setOnPreferenceClickListener(preference -> {
                    if (!PermissionAccess.isNotificationListenerEnabled(getContext())) {
                        PermissionAccess.requestNotificationListenerAccess(getContext(), "", true, null, MyNotificationListener.class);
                    }
                    return true;
                });
            }
            switch_unknown_sources = findPreference("switch_unknown_sources");
            if (switch_unknown_sources != null) {
                switch_unknown_sources.setAllowed(PermissionAccess.isInstallUnknownAppsAllowed(getContext()));
                switch_unknown_sources.setAcceptedText("Permission Granted");
                switch_unknown_sources.setRequestText("Please Allow");
                switch_unknown_sources.setOnPreferenceClickListener(preference -> {
                    if (!PermissionAccess.isInstallUnknownAppsAllowed(getContext()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        PermissionAccess.requestInstallUnknownAppsAccess(getContext(), "", true, null, null);
                    }
                    return true;
                });
            }


            // ===================== Media Projection =====================
            mediaProjectionLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                    Toast.makeText(getContext(), "Screen capture permission granted!", Toast.LENGTH_SHORT).show();
                    switch_media_projection.setAllowed(true);
                } else {
                    Toast.makeText(getContext(), "Screen capture permission denied", Toast.LENGTH_SHORT).show();
                    switch_media_projection.setAllowed(false);
                }
            });

            switch_media_projection = findPreference("switch_media_projection");
            if (switch_media_projection != null) {
                switch_media_projection.setAllowed(false);
                switch_media_projection.setAcceptedText("Permission Granted");
                switch_media_projection.setRequestText("Please Allow");
                switch_media_projection.setOnPreferenceClickListener(pref -> {
                    PermissionAccess.requestMediaProjectionAccess(getContext(), "", true, null, null);

                    return true;
                });
            }
        }


        @Override
        public void onResume() {
            super.onResume();

            switch_device_admin.setAllowed(PermissionAccess.isDeviceAdminEnabled(getContext(), MyDeviceAdminReceiver.class));

            switch_accessibility.setAllowed(PermissionAccess.isAccessibilityServiceEnabled(getContext(), MyAccessibilityService.class));

            switch_manage_storage.setAllowed(PermissionAccess.isManageStorageGranted(getContext()));

            switch_overlay.setAllowed(PermissionAccess.isSystemAlertGranted(getContext()));

            switch_battery_opt.setAllowed(PermissionAccess.isIgnoreBatteryOptimization(getContext()));

            switch_usage_access.setAllowed(PermissionAccess.isUsageStatsGranted(getContext()));

            switch_write_settings.setAllowed(PermissionAccess.isModifySettingsGranted(getContext()));

            switch_dnd.setAllowed(PermissionAccess.isDontDistrubGranted(getContext()));

            switch_notification_listener.setAllowed(PermissionAccess.isNotificationListenerEnabled(getContext()));

            switch_unknown_sources.setAllowed(PermissionAccess.isInstallUnknownAppsAllowed(getContext()));

        }

    }
}