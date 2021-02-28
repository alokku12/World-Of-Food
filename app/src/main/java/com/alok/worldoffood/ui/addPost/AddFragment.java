package com.alok.worldoffood.ui.addPost;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alok.worldoffood.MainActivity;
import com.alok.worldoffood.R;
import com.alok.worldoffood.adapters.GalleryImageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.util.UUID;

import static androidx.navigation.Navigation.findNavController;

public class AddFragment extends Fragment implements GalleryImageAdapter.GalleryItemListener {

    Uri imageUri;

    public static final int REQUEST_CODE=100;

    String imagePath;

    private static final String TAG = "NotificationsFragment";



    private AddViewModel addViewModel;

    NavHostController navHostController;

    RecyclerView recyclerView;
    GalleryImageAdapter galleryImageAdapter;

    boolean imageSelected= false;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addViewModel =
                new ViewModelProvider(this).get(AddViewModel.class);
        root = inflater.inflate(R.layout.fragment_notifications, container, false);

         ImageView cameraBtn = root.findViewById(R.id.camera_button);

        ImageView postButton = root.findViewById(R.id.go_to_post);

        if(imageSelected){
            postButton.setVisibility(View.VISIBLE);
        }else {
            postButton.setVisibility(View.INVISIBLE);
        }




        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              ;


                findNavController(getActivity(),R.id.nav_host_fragment).navigate(AddFragmentDirections.actionNavigationAddToPostFragment(imagePath));

                 imageSelected = false;





            }
        });

        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageSelected =true;


                ContentValues values = new ContentValues();

                values.put(MediaStore.Images.Media.TITLE,"New Picture");
                values.put(MediaStore.Images.Media.DESCRIPTION,"FROM THE CAMERA");

                imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(takePictureIntent, REQUEST_CODE);








            }
        });

        recyclerView = root.findViewById(R.id.gallery_recyclerview);
        galleryImageAdapter = new GalleryImageAdapter(getActivity(), this);
        galleryImageAdapter.setTheGallery();
        recyclerView.setAdapter(galleryImageAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false));







        addViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });
        return root;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode ==REQUEST_CODE ) {


            ImageView imageView = root.findViewById(R.id.post_photo);
            Picasso.get().load(imageUri).resize(400, 400).into(imageView);



            imagePath = imageUri.toString();

           // uploadToFirebase(getActivity());






         //   Log.d(TAG, "onActivityResult: "+imagePath+" "+imageUri);



            ImageView postButton = root.findViewById(R.id.go_to_post);
            imageSelected = true;
            if (imageSelected) {
                postButton.setVisibility(View.VISIBLE);
            } else {
                postButton.setVisibility(View.INVISIBLE);
            }
        }




    }

    @Override
    public void onGalleryItemClick(int position,String path) throws FileNotFoundException {

        Log.d(TAG, "onGalleryItemClick: "+position);


        ImageView imageView = root.findViewById(R.id.post_photo);

        imagePath =path;



        Picasso.get().load(path).resize(400,400).centerCrop().into(imageView);

        ImageView postButton =root.findViewById(R.id.go_to_post);

        imageSelected = true;
        if(imageSelected){
            postButton.setVisibility(View.VISIBLE);
        }else {
            postButton.setVisibility(View.INVISIBLE);
        }








       // Toast.makeText(getContext(),position,Toast.LENGTH_LONG).show();

    }

    Uri URL;


    public void uploadToFirebase( Activity activity){




        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference mountainsRef = storageRef.child("/feeds/images/"+ UUID.randomUUID());
        UploadTask uploadTask = mountainsRef.putFile(imageUri);


        uploadTask.addOnSuccessListener(activity, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String URL = uri.toString();
                        Log.d(TAG, "onSuccess: "+URL);
                    }
                });



            }
        });



    }
}