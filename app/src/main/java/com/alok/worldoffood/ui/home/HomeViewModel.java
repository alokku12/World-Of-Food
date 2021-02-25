package com.alok.worldoffood.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alok.worldoffood.R;
import com.alok.worldoffood.room.entity.Feeds;
import com.alok.worldoffood.room.repository.FeedRepository;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends AndroidViewModel {

    private LiveData<List<Feeds>> feedsList ;

    FeedRepository feedRepository ;

    List<Feeds>   feeds;



    public HomeViewModel(@NonNull Application application) {
        super(application);
        feeds = new ArrayList<>();

        feedRepository = FeedRepository.getFeedRepository(application);

        feedsList =  feedRepository.getAllFeeds();



    }

    public LiveData<List<Feeds>> getFeedsList() {
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