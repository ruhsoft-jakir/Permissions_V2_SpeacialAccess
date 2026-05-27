package com.jakir.permissions_v2_speacialaccess.listner;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

public class MyAccessibilityService extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        // এখানে তুই নিজের ইভেন্ট হ্যান্ডলিং কোড লিখবি
    }

    @Override
    public void onInterrupt() {
        Toast.makeText(this, "Accessibility Service Interrupted", Toast.LENGTH_SHORT).show();
    }
}
