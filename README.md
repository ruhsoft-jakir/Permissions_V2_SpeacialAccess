
# 📌 Permissions_V2            – Android System Permission Manager  

A powerful and easy-to-use Android library to handle **special/system accesses** with clean APIs and modern Material dialogs (BottomSheets).  

---

## ✨ Features  

### ⚙️ Special System Permissions & Access  
- Device Admin  
- Accessibility Service  
- Display over other apps (Overlay)  
- All Files Access (Manage External Storage)  
- Ignore Battery Optimization  
- Usage Access  
- Modify System Settings  
- Do Not Disturb Access  
- Install Unknown Apps  
- Notification Listener Access  
- Media Projection (Screen Capture)  
- GPS Enable  

---

## 🚀 Installation  

### LATEST-VERSION
[![](https://jitpack.io/v/ruhsoft-jakir/Permissions_V2_SpeacialAccess.svg)](https://jitpack.io/#ruhsoft-jakir/Permissions_V2_SpeacialAccess)

Add it in your `settings.gradle` at the end of repositories:
```gradle
//dependencyResolutionManagement {
//    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
//    repositories {
//        google()
//        mavenCentral()
        maven { url 'https://jitpack.io' }
//    }
//}

```
Add on dependency via **Gradle**  `build.gradle`  (jitpack.io support):  

```gradle
dependencies {
	        implementation 'com.github.ruhsoft-jakir:Permissions_V2_SpeacialAccess:Tag'
}
```
#### LATEST-VERSION
[![](https://jitpack.io/v/ruhsoft-jakir/Permissions_V2_SpeacialAccess.svg)](https://jitpack.io/#ruhsoft-jakir/Permissions_V2_SpeacialAccess)


*(If not published yet, you can import `.aar` / `.module` locally.)*  

---

## 🛠 Usage  

### ⚙️ Check and Request Special Access  

```java
// Check and Request Device Admin with BottomSheet Dialog 
        if (!PermissionAccess.isDeviceAdminEnabled(this, MyDeviceAdminReceiver.class)) {
    PermissionAccess.requestDeviceAdminAccess(
            this,
            "", // BottomSheet dialog message
            true, // if you want to show BottomSheet dialog keep true, otherwise false
            null, // BottomSheet dialog image
            MyDeviceAdminReceiver.class);
}
```

#### All others:

```java
// Request GPS (Location service must be enabled)
        if (!PermissionAccess.isGPSAccessEnabled(this)) {
        PermissionAccess.requestGpsEnabledAccess(this, "", true, null, null);
}
```

```java
// Request Device Admin access
        if (!PermissionAccess.isDeviceAdminEnabled(this, MyDeviceAdminReceiver.class)) {
        PermissionAccess.requestDeviceAdminAccess(this, "", true, null, MyDeviceAdminReceiver.class);
}
```

```java
// Request Accessibility Service permission
        if (!PermissionAccess.isAccessibilityServiceEnabled(this, MyAccessibilityService.class)) {
        PermissionAccess.requestAccessibilityAccess(this, "", true, null, MyAccessibilityService.class);
}
```

```java
// Request Manage External Storage (All files access, Android 11+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && !PermissionAccess.isManageStorageGranted(this)) {
        PermissionAccess.requestManageStorageAccess(this, "", true, null, null);
}
```

```java
// Request Overlay permission (Draw over other apps)
        if (!PermissionAccess.isSystemAlertGranted(this)) {
        PermissionAccess.requestSystemAlertAccess(this, "", true, null, null);
}
```

```java
// Request Ignore Battery Optimization (to prevent app from being killed)
        if (!PermissionAccess.isIgnoreBatteryOptimization(this)) {
        PermissionAccess.requestBatteryOptimizationAccess(this, "", true, null, null);
}
```

```java
// Request Usage Stats Access (App usage tracking permission)
        if (!PermissionAccess.isUsageStatsGranted(this)) {
        PermissionAccess.requestUsagesAccessAccess(this, "", true, null, null);
}
```

```java
// Request Modify System Settings permission
        if (!PermissionAccess.isModifySettingsGranted(this)) {
        PermissionAccess.requestModifySystemSettingsAccess(this, "", true, null, null);
}
```

```java
// Request Do Not Disturb (DND) access
        if (!PermissionAccess.isDontDistrubGranted(this)) {
        PermissionAccess.requestDontDistrubAccessAccess(this, "", true, null, null);
}
```

```java
// Request Notification Listener Service access
        if (!PermissionAccess.isNotificationListenerEnabled(this)) {
        PermissionAccess.requestNotificationListenerAccess(this, "", true, null, MyNotificationListener.class);
}
```

```java
// Request Install Unknown Apps permission (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !PermissionAccess.isInstallUnknownAppsAllowed(this)) {
        PermissionAccess.requestInstallUnknownAppsAccess(this, "", true, null, null);
}

```

---

## 📂 Helpers  

 - `PermissionAccess_helper` → System accesses (GPS, Overlay, Device Admin, etc.) with name, message, animation  
- Built-in **BottomSheet dialogs** for better UX  

---

## 🎨 UI/UX  

- Material Design BottomSheet dialogs  
- Lottie animations (`.raw` resources) for each permission  
- Customizable icons & messages  

---

## 🎥 Demo  

Here’s how it looks in action 👇  

 
### ⚙️ Special Access  


| Device Admin Access                         | All Files Access                              | Usage Access                                | Notification Listener              | Don't Disturb Access                      |
|---------------------------------------------|-----------------------------------------------|---------------------------------------------|--------------------------------------------|-------------------------------------------|
| ![Device Admin Demo](screenshots/admin.png) | ![Device Admin Demo](screenshots/allfile.png) | ![Device Admin Demo](screenshots/usage.png) | ![Device Admin Demo](screenshots/noti.png) | ![Device Admin Demo](screenshots/dnd.png) |
---



## 📲 Download Sample
 ![Click to Download App](app/build/outputs/apk/debug/app-debug.apk)


## 🤝 Contributing  

Pull requests are welcome! If you find any bug or missing permission, open an issue or create a PR.  

---

## 📜 License  

This library is released under the **MIT License**.  
