package com.jakir.permissions_v2_speacialaccess.listner;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

public class MyNotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // নোটিফিকেশন পোস্ট হলে এখানে হ্যান্ডল করবি
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        Toast.makeText(this, "Notification Removed", Toast.LENGTH_SHORT).show();
    }
}
