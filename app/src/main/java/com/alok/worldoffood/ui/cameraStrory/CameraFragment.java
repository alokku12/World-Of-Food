package com.alok.worldoffood.ui.cameraStrory;

import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alok.worldoffood.R;
import com.alok.worldoffood.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.util.concurrent.ListenableFuture;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class CameraFragment extends Fragment {

    private CameraViewModel mViewModel;

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    private static final String TAG = "CameraFragment";
    BottomNavigationView bottomNavigationView;



    PreviewView previewView;
    View v;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

         bottomNavigationView = getActivity().findViewById(R.id.nav_view);
        bottomNavigationView.setVisibility(View.INVISIBLE);



        v = inflater.inflate(R.layout.fragment_camera, container, false);

        ImageView cameraButton = v. findViewById(R.id.camera_capture_button);
        startCamera();

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });

         //previewView = v.findViewById(R.id.viewFinder);




       return v;


    }
    private ImageCapture imageCapture;

    private void startCamera() {

        PreviewView previewView = v.findViewById(R.id.viewFinder);

        ListenableFuture cameraProviderFuture =
                ProcessCameraProvider.getInstance(getContext());

        cameraProviderFuture.addListener(() -> {
            try {
                // Camera provider is now guaranteed to be available
                ProcessCameraProvider cameraProvider = (ProcessCameraProvider) cameraProviderFuture.get();

                // Set up the view finder use case to display camera preview
                Preview preview = new Preview.Builder().build();

                // Set up the capture use case to allow users to take photos
                imageCapture = new ImageCapture.Builder()
                        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                        .build();

                // Choose the camera by requiring a lens facing
                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build();

                // Attach use cases to the camera with the same lifecycle owner
                cameraProvider.bindToLifecycle(
                        ((LifecycleOwner) this),
                        cameraSelector,
                        preview,
                        imageCapture);

                // Connect the preview use case to the previewView
                preview.setSurfaceProvider(
                        previewView.getSurfaceProvider());
            } catch (InterruptedException | ExecutionException e) {
                // Currently no exceptions thrown. cameraProviderFuture.get()
                // shouldn't block since the listener is being called, so no need to
                // handle InterruptedException.
            }
        }, ContextCompat.getMainExecutor(getContext()));
    }

    private static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";


    Uri imageUri;
    public void takePicture() {



        File[] mediaDirs = getContext().getExternalMediaDirs();

        File photoFile = new File(mediaDirs[0],"images");

        if(!photoFile.exists()){
            photoFile.mkdir();
        }


        File outputDirectory =  new File(photoFile,getResources().
                getString(R.string.app_name)+" "+new SimpleDateFormat(FILENAME_FORMAT,Locale.US).format(System.currentTimeMillis())+ ".jpg") ;

//        File outputDirectory =  new File(Environment.getExternalStorageDirectory(),getResources().
//                getString(R.string.app_name)+" "+new SimpleDateFormat(FILENAME_FORMAT,Locale.US).format(System.currentTimeMillis())+ ".jpg") ;

        Log.d(TAG, "takePicture: "+outputDirectory);




            ImageCapture.OutputFileOptions outputFileOptions =
               new ImageCapture.OutputFileOptions.Builder(outputDirectory).build();


        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(getContext()), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {

                Utils.showMessage(getContext(),"Image Captured");

            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {

                Utils.showMessage(getContext(),"failed");
            }
        });



    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CameraViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bottomNavigationView.setVisibility(View.VISIBLE);

    }


}



