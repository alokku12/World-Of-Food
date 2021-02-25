package com.alok.worldoffood.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alok.worldoffood.R;
import com.alok.worldoffood.adapters.FeedAdapters;
import com.alok.worldoffood.adapters.GalleryImageAdapter;
import com.alok.worldoffood.room.entity.Feeds;
import com.alok.worldoffood.ui.home.HomeViewModel;

import java.io.FileNotFoundException;
import java.util.List;

public class ProfileFragment extends Fragment implements GalleryImageAdapter.GalleryItemListener{

    private ProfileViewModel mViewModel;




    ProfileViewModel.Factory factory;
    RecyclerView userList;

    GalleryImageAdapter galleryImageAdapter;

    GalleryImageAdapter.GalleryItemListener galleryItemListener;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {



        galleryItemListener = this;

        View v = inflater.inflate(R.layout.profile_fragment, container, false);

        factory = new ProfileViewModel.Factory(getActivity().getApplication());
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);




        mViewModel.getFeedsList().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {

                galleryImageAdapter = new GalleryImageAdapter(getActivity(),galleryItemListener);
                galleryImageAdapter.setTheProfilePostList(strings);

                userList = v.findViewById(R.id.user_feed_list);

                userList.setAdapter(galleryImageAdapter);
                userList.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false));
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // TODO: Use the ViewModel
    }

    @Override
    public void onGalleryItemClick(int position, String path) {

    }
}