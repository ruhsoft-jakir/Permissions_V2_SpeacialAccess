package com.jakir.permissions.dialogs;

import static android.view.View.VISIBLE;
import static com.jakir.permissions.PermissionAccess.requestMediaProjection;
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

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.jakir.permissions.PermissionAccess;
import com.jakir.permissions.PermissionAccess_helper;
import com.jakir.permissions.R;

//
// Created by JAKIR HOSSAIN on 8/15/2025.
//
public class Bottomsheet_dialog_Access {
    LottieAnimationView lottie;
    TextView tittle_tv, tittle_tvi, message_tv;
    ImageView close_iv, Primage;
    Context context;
    Button allow_btn;

    public Bottomsheet_dialog_Access(Context context) {
        this.context = context;

    }

    public void show(int allow_code, String message, Drawable permissionImage, Class<?> classService) {
        BottomSheetDialog dialog;
        View view = ((Activity) context).getLayoutInflater().inflate(R.layout.permission_bottomsheet_layout, null);
        dialog = new BottomSheetDialog(context, R.style.AppBottomSheetDialogTheme); // Style here
        dialog.setContentView(view);
        dialog.show();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setNavigationBarColor(ContextCompat.getColor(context, R.color.DayLight_NightDark)); //✅ NavigationBar color fix
        }
        String pDisplayName = PermissionAccess_helper.getAccessPermissionName(allow_code);

        if (permissionImage != null) {
            tittle_tvi = dialog.findViewById(R.id.tittle_tvi);
            Primage = dialog.findViewById(R.id.Primage);

            Primage.setImageDrawable(permissionImage);
            tittle_tvi.setVisibility(VISIBLE);
            tittle_tvi.setText(pDisplayName);
            Primage.setVisibility(VISIBLE);
        } else {
            tittle_tv = dialog.findViewById(R.id.tittle_tv);
            lottie = dialog.findViewById(R.id.lottie);

            lottie.setAnimation(PermissionAccess_helper.getAccessPermissionAnim(allow_code));
            lottie.playAnimation();
            tittle_tv.setVisibility(VISIBLE);
            tittle_tv.setText(pDisplayName);
            lottie.setVisibility(VISIBLE);
        }

        message_tv = dialog.findViewById(R.id.message_tv);
        message_tv.setText(message.isEmpty() ? PermissionAccess_helper.getAccessPermissionMessage(allow_code) : message);

        allow_btn = dialog.findViewById(R.id.allow_btn);
        allow_btn.setOnClickListener(view1 -> {
            dialog.dismiss();
            perfromAction(allow_code, classService);
        });
        close_iv = dialog.findViewById(R.id.close_iv);
        close_iv.setOnClickListener(v -> dialog.dismiss());
    }

    public void Notshow(int allow_code, Class<?> classService) {
        perfromAction(allow_code, classService);
    }

    private void perfromAction(int allowCode, Class<?> classService) {
        switch (allowCode) {
            case REQUEST_CODE_GPS:
                if (!PermissionAccess.isGPSAccessEnabled(context)) {
                    PermissionAccess.requestGpsEnabled(context);
                }
                break;
            case REQUEST_CODE_DEVICE_ADMIN:
                if (classService != null) {
                    if (!PermissionAccess.isDeviceAdminEnabled(context, classService)) {
                        PermissionAccess.requestDeviceAdmin(context, classService);
                    }
                }
                break;
            case REQUEST_CODE_ACCESSIBILITY:
                if (classService != null) {
                    if (!PermissionAccess.isAccessibilityServiceEnabled(context, classService)) {
                        PermissionAccess.requestAccessibility(context);
                    }
                }
                break;
            case REQUEST_CODE_OVERLAY:
                if (!PermissionAccess.isSystemAlertGranted(context)) {
                    PermissionAccess.requestSystemAlert(context);
                }
                break;
            case REQUEST_CODE_BATTERY_OPT:

                if (!PermissionAccess.isIgnoreBatteryOptimization(context)) {
                    PermissionAccess.requestBatteryOptimization(context);
                }
                break;
            case REQUEST_CODE_USAGE_ACCESS:

                if (!PermissionAccess.isUsageStatsGranted(context)) {
                    PermissionAccess.requestUsages(context);
                }
                break;
            case REQUEST_CODE_MODIFY_SYSTEM_SETTINGS:

                if (!PermissionAccess.isModifySettingsGranted(context)) {
                    PermissionAccess.requestModifySystemSettings(context);
                }
                break;
            case REQUEST_CODE_DONT_DISTURB:

                if (!PermissionAccess.isDontDistrubGranted(context)) {
                    PermissionAccess.requestDontDistrub(context);
                }
                break;
            case REQUEST_CODE_INSTALL_UNKNOWN_APPS:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !PermissionAccess.isInstallUnknownAppsAllowed(context)) {
                    PermissionAccess.requestInstallUnknownApps(context);
                }
                break;
            case REQUEST_CODE_NOTIFICATION_LISTENER:
                if (classService != null) {
                    if (!PermissionAccess.isNotificationListenerEnabled(context)) {
                        PermissionAccess.requestNotificationListener(context, classService);
                    }
                }
                break;
            case REQUEST_CODE_MANAGESTORAGE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !PermissionAccess.isManageStorageGranted(context)) {
                    PermissionAccess.requestManageStorage(context);
                }
                break;

            case REQUEST_CODE_MEDIA_PROJECTION:
                requestMediaProjection(context);
                break;

        }

    }
}
