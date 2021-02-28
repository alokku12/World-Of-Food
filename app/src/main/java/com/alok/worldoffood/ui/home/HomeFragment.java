package com.alok.worldoffood.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alok.worldoffood.R;
import com.alok.worldoffood.adapters.FeedAdapters;
import com.alok.worldoffood.room.entity.Feeds;
import com.alok.worldoffood.room.repository.FeedRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private HomeViewModel homeViewModel;
    HomeViewModel.Factory factory;



    FeedAdapters feedAdapter;
    ArrayList<Feeds> feeds;

    RecyclerView recyclerView;



    List<Feeds> feedsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        factory = new HomeViewModel.Factory(getActivity().getApplication());
        homeViewModel =
                new ViewModelProvider(this,factory).get(HomeViewModel.class);


        View root = inflater.inflate(R.layout.fragment_home, container, false);

        AppCompatImageView cameraButton = root.findViewById(R.id.camera_button_in_home);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Navigation.findNavController(getActivity(), R.id.nav_host_fragment).
                      navigate(HomeFragmentDirections.actionNavigationFeedToCameraFragment2());

            }
        });




        homeViewModel.getFeedsList().observe(getViewLifecycleOwner(), new Observer<List<Feeds>>() {
            @Override
            public void onChanged(List<Feeds> feeds) {

                feedAdapter = new FeedAdapters(getActivity());

                feedAdapter.setFeedList(feeds);

                recyclerView = root.findViewById(R.id.recycler_view);

                recyclerView.setAdapter(feedAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }
        });








        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }
}