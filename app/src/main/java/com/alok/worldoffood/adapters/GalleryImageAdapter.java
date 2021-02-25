package com.alok.worldoffood.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alok.worldoffood.R;
import com.alok.worldoffood.modelClasses.GalleryImages;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.GalleryImageHolder> {

    Context context;


    List<String> galleryList;
    LayoutInflater layoutInflater;
    GalleryItemListener listener;

    private static final String TAG = "GalleryImageAdapter";



    public  GalleryImageAdapter(Context context,GalleryItemListener listener){

        this.context=context;
        layoutInflater = LayoutInflater.from(context);
        this.listener=listener;
    }

    public void setTheGallery(){

        galleryList = GalleryImages.listOfImages(context);
        Log.d(TAG, "setTheGallery: "+galleryList.toString());

    }

    public void setTheProfilePostList(List<String> profileGallery){

        galleryList = profileGallery;
        Log.d(TAG, "setTheGallery: "+galleryList.toString());

    }

    @NonNull
    @Override
    public GalleryImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.gallery_item,parent,false);




        return new GalleryImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryImageHolder holder, int position) {

      //  imageView.setImageResource(R.drawable.feed);



        Picasso.get().load("file://"+galleryList.get(position)).centerCrop().resize(200,200).into(holder.imageView);

     //   Glide.with(context).load(galleryList.get(position)).fitCenter().into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    listener.onGalleryItemClick(position,"file://"+galleryList.get(position));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });




      //  load("file://"+path)

    }

    @Override
    public int getItemCount() {
        return galleryList.size();
    }

    public static class GalleryImageHolder extends RecyclerView.ViewHolder {
        ImageView imageView;


        public GalleryImageHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.gallery_image);
        }
    }



    public interface GalleryItemListener{

        void onGalleryItemClick(int position,String path) throws FileNotFoundException;
    }
}
