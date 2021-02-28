package com.alok.worldoffood.utils;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

public class Utils {


    private  static String mProfileName = "alok";



    public static  String getProfileName() {
        return mProfileName;
    }

    public static  void setProfileName(String profileName) {
       mProfileName =profileName;

    }

    public static void showMessage(Context context , String message){

        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }



}
