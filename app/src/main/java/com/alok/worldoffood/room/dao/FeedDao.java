package com.alok.worldoffood.room.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.alok.worldoffood.room.entity.Feeds;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface FeedDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addFeedItem(Feeds feeds);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAllFeedItem(List<Feeds> foodItemsList);

    @Query("UPDATE FEEDS_TABLE SET `like`=:likedImage WHERE feed_id= :ID")
    void updateLike(long ID,boolean likedImage);

    @Query("DELETE FROM feeds_table")
    void deleteAll();

    @Query("DELETE FROM feeds_table where feed_id= :ID")
    void deleteFromId(long ID);

    @Query("SELECT * FROM feeds_table")
    LiveData <List<Feeds>> getAllFeed();

    @Query("SELECT feed_image from feeds_table where profile_name = 'alok'")
    LiveData<List<String>> getAllFeedFromUser();


}
