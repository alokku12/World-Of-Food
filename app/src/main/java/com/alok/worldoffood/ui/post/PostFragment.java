package com.alok.worldoffood.ui.post;

import androidx.lifecycle.ViewModelProvider;

import android.inputmethodservice.ExtractEditText;
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

import com.alok.worldoffood.R;
import com.alok.worldoffood.room.entity.Feeds;

import com.alok.worldoffood.room.repository.FeedRepository;
import com.alok.worldoffood.ui.home.HomeViewModel;
import com.alok.worldoffood.utils.Utils;
import com.squareup.picasso.Picasso;

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




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        factory = new PostViewModel.Factory(getActivity().getApplication());
        postViewModel =  new ViewModelProvider(this,factory).get(PostViewModel.class);

        if(getArguments()!=null) {

          args  = PostFragmentArgs.fromBundle(getArguments());

        }
        String path =  args.getImagePath();





        View v =  inflater.inflate(R.layout.post_fragment, container, false);

        EditText extractEditText = v.findViewById(R.id.caption_edit_text);

        ImageView imageToBePosted = v.findViewById(R.id.image_to_be_posted);
        Picasso.get().load(path).resize(100,100).centerCrop().into(imageToBePosted);




        ImageView postTheImage = v.findViewById(R.id.post_the_image);

        postTheImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String captionText = extractEditText.getText().toString();
                Feeds feeds = new Feeds(Utils.getProfileName(),"Delhi",path,captionText,false,false);

                Log.d(TAG, "onClick: "+path+" "+captionText);



                postViewModel.postFeed(feeds);

                findNavController(getActivity(),R.id.nav_host_fragment).
                        navigate(PostFragmentDirections.actionPostFragmentToNavigationFeed());




              //  findNavController(getActivity(),R.id.nav_host_fragment).popBackStack();






            }
        });











        return  v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        // TODO: Use the ViewModel
    }

}