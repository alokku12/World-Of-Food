package com.alok.worldoffood.utils;

import android.app.Application;

public class Utils {


    private  static String mProfileName = "alok";



    public static  String getProfileName() {
        return mProfileName;
    }

    public static  void setProfileName(String profileName) {
       mProfileName =profileName;

    }



}
