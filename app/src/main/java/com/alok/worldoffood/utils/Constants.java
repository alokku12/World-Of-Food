package com.alok.worldoffood.utils;

import android.Manifest;

public class Constants {


    //related app permissions
    // The request code used in ActivityCompat.requestPermissions()
    // and returned in the Activity's onRequestPermissionsResult()
    public static final int PERMISSION_ALL = 1;

    public static final String[] PERMISSIONS = {
            Manifest.permission.CAMERA,// 0
            Manifest.permission.WRITE_EXTERNAL_STORAGE,// 1
            Manifest.permission.READ_EXTERNAL_STORAGE, // 2
    };

    public static final int USE_CAMERA = 123;
    public static final int FILE_STORAGE_PERMISSION = 4214;
    public static final int FILE_PERMISSION = 3214;

    public static final Integer REQUEST_TIME_DELAY = 10000;

    //related to shared preferences file storing the data
    public static final String LOGIN_PREFS = "LoginDetails";
    public static final String LOGIN_TOKEN = "loginToken";
    public static final String USER_ID = "userId";
    public static final String FCM_TOKEN = "fcm_token";

    //related to general parameters
    public static final String CUSTOMER_ID = "customer_id";
    public static final String CUSTOMER_DATA = "customer_data";

    //related to fcm
    public static final String FCM_NOTIFICATION = "FCM_NOTIFICATION";
    public static final String FCM_CUSTOMER_ID = "customer_id";
    public static final String FCM_CONTENT = "content";
    public static final String FCM_CUSTOMER = "customer";

    //broadcast related to fcm
    public static final String FCM_CHAT_BROADCAST = "FCM_CHAT_BROADCAST";

    //related to whatsapp templates
    public static final String TEMPLATE_REGEX_PLACEHOLDER = "\\^\\&";

    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final String CUSTOMER = "customer";
}
