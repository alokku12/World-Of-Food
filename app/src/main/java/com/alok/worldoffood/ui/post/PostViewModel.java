package com.alok.worldoffood.ui.post;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alok.worldoffood.room.entity.Feeds;
import com.alok.worldoffood.room.repository.FeedRepository;
import com.alok.worldoffood.ui.home.HomeViewModel;

public class PostViewModel extends AndroidViewModel {

    FeedRepository feedRepository ;



    public void postFeed(Feeds feeds){

        feedRepository.insertFeeds(feeds);

    }




    public PostViewModel(@NonNull Application application) {
        super(application);

        feedRepository = FeedRepository.getFeedRepository(application);
    }
    // TODO: Implement the ViewModel

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        public Factory(@NonNull Application application) {
            mApplication = application;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //no inspection unchecked
            return (T) new PostViewModel(mApplication);
        }
    }
}