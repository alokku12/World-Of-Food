package com.alok.worldoffood.room.repository;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.alok.worldoffood.retrofit.ApiInterface;
import com.alok.worldoffood.retrofit.RetrofitClient;
import com.alok.worldoffood.room.WorldOfFoodDatabase;
import com.alok.worldoffood.room.dao.FeedDao;
import com.alok.worldoffood.room.entity.Feeds;

import java.util.List;

public class FeedRepository {

    private static final String TAG = "FeedRepository";



    Context context;

    WorldOfFoodDatabase db;
    FeedDao mFeedDao;

    ApiInterface apiInterface;

    private static volatile FeedRepository INSTANCE;




    private FeedRepository(Context context) {
        this.context = context;
        this.db = WorldOfFoodDatabase.getDatabaseInstance(context);
        this.mFeedDao = db.feedDao();

        this.apiInterface = RetrofitClient
                .getRetrofitInstance(context.getApplicationContext())
                .create(ApiInterface.class);
    }

    public static FeedRepository getFeedRepository(final Context context) {
        if (INSTANCE == null) {
            synchronized (FeedRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FeedRepository(context);
                }
            }
        }
        return INSTANCE;
    }

    public void insertFeeds(Feeds feeds){

        new InsertFeedAsyncTask(mFeedDao).execute(feeds);



    }

    public LiveData<List<Feeds>>  getAllFeeds(){








        return mFeedDao.getAllFeed();
    }

    public LiveData<List<String>> getAllFeedImagesFromUser(){

        return mFeedDao.getAllFeedFromUser();
    }


    public void deleteFromId(long ID){

        mFeedDao.deleteFromId(ID);
    }


    private static class InsertFeedAsyncTask extends AsyncTask<Feeds, Void, Void> {

        private FeedDao mAsyncFeedDao;

        InsertFeedAsyncTask(FeedDao dao) {
            mAsyncFeedDao = dao;
        }


        @Override
        protected Void doInBackground(Feeds... feeds) {
            mAsyncFeedDao.addFeedItem(feeds[0]);

            return null;
        }
    }

    private static List<Feeds> feedsList;

//    private static class GetAllFeedAsyncTask extends AsyncTask<Feeds, Void, List<Feeds>> {
//
//        private FeedDao mAsyncFeedDao;
//
//        GetAllFeedAsyncTask(FeedDao dao) {
//            mAsyncFeedDao = dao;
//        }
//
//
//        @Override
//        protected List<Feeds>  doInBackground(Feeds... feeds) {
//
//
//
//
//
//        }
//
//        @Override
//        protected void onPostExecute(List<Feeds> feeds) {
//
//            feedsList = feeds;
//        }
//    }











}
