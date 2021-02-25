package com.alok.worldoffood.ui.profile;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alok.worldoffood.room.entity.Feeds;
import com.alok.worldoffood.room.repository.FeedRepository;
import com.alok.worldoffood.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private LiveData<List<String>> feedsList ;

    private static final String TAG = "ProfileViewModel";

    FeedRepository feedRepository ;

    List<Feeds>   feeds;




    public ProfileViewModel(@NonNull Application application) {
        super(application);
        feeds = new ArrayList<>();

        feedRepository = FeedRepository.getFeedRepository(application);

        feedsList =  feedRepository.getAllFeedImagesFromUser();

        Log.d(TAG, "ProfileViewModel: "+feedsList);



    }

    public LiveData<List<String>> getFeedsList() {

        Log.d(TAG, "getFeedsList: "+feedsList);
        return feedsList;
    }


    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //no inspection unchecked
            return (T) new HomeViewModel(mApplication);
        }
    }
}