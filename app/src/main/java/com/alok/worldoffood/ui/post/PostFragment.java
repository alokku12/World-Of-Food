package com.alok.worldoffood.ui.post;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.inputmethodservice.ExtractEditText;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.alok.worldoffood.R;
import com.alok.worldoffood.room.entity.Feeds;

import com.alok.worldoffood.room.repository.FeedRepository;
import com.alok.worldoffood.ui.home.HomeViewModel;
import com.alok.worldoffood.utils.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import static androidx.navigation.Navigation.findNavController;

public class PostFragment extends Fragment {

    private PostViewModel mViewModel;
    private static final String TAG = "PostFragment";


    public static PostFragment newInstance() {
        return new PostFragment();
    }

    PostFragmentArgs args;
    PostViewModel postViewModel;

    PostViewModel.Factory factory;
    String documentId="";

    ProgressBar progressBar ;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        factory = new PostViewModel.Factory(getActivity().getApplication());
        postViewModel = new ViewModelProvider(this, factory).get(PostViewModel.class);

        if (getArguments() != null) {

            args = PostFragmentArgs.fromBundle(getArguments());

        }
        String path = args.getImagePath();


        View v = inflater.inflate(R.layout.post_fragment, container, false);

        EditText extractEditText = v.findViewById(R.id.caption_edit_text);

        ImageView imageToBePosted = v.findViewById(R.id.image_to_be_posted);
        Picasso.get().load(path).resize(100, 100).centerCrop().into(imageToBePosted);


        ImageView postTheImage = v.findViewById(R.id.post_the_image);

        progressBar = v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);



        postTheImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String captionText = extractEditText.getText().toString();



                uploadToFirebaseAndSyncWithDatabase(getActivity(),path,captionText);




                //  findNavController(getActivity(),R.id.nav_host_fragment).popBackStack();


            }
        });


        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        // TODO: Use the ViewModel
    }


    public void uploadToFirebaseAndSyncWithDatabase( Activity activity,String path,String captionText){



        progressBar.setVisibility(View.VISIBLE);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference mountainsRef = storageRef.child("/feeds/images/"+ UUID.randomUUID());


        UploadTask uploadTask = mountainsRef.putFile(Uri.parse(path));


        uploadTask.addOnSuccessListener(activity, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                mountainsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String URL = uri.toString();
                        Log.d(TAG, "onSuccess: "+URL);

                        Feeds feeds = new Feeds(Utils.getProfileName(), "Delhi", URL, captionText, false, false);

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        db.collection("feeds").add(feeds).
                                addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                                        documentId = documentReference.getId();
                                        Log.d(TAG, "onSuccess: "+documentId);
                                        feeds.setFeed_id(documentId);
                                        postViewModel.postFeed(feeds);
                                        findNavController(getActivity(), R.id.nav_host_fragment).
                                                navigate(PostFragmentDirections.actionPostFragmentToNavigationFeed());


                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error adding document", e);
                                    }
                                });

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+"error getting download link");
                    }
                });



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Utils.showMessage(getActivity(),"Upload failed");
            }
        });



    }

}