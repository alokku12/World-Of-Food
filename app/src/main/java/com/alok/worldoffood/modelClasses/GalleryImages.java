package com.alok.worldoffood.modelClasses;

import android.content.Context;
import android.database.Cursor;
import android.media.MediaActionSound;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class GalleryImages {


    public static ArrayList<String> listOfImages(Context context){


        Uri uri;

        Cursor cursor;


        int column_index_data,column_index_folder_name;

        ArrayList<String> listOfAllImages = new ArrayList<>();


        String absolutePathOfImage;

        uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String [] projections = {
                MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
        };

        String orderBy = MediaStore.Video.Media.DATE_TAKEN;

        cursor= context.getContentResolver().query(uri,projections,null,null,orderBy+" DESC");

        column_index_data= cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);




        while (cursor.moveToNext()){

            absolutePathOfImage=cursor.getString(column_index_data);
            listOfAllImages.add(absolutePathOfImage);


        }


        return listOfAllImages;

    }
}
