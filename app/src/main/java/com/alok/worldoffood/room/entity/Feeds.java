package com.alok.worldoffood.room.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "feeds_table")
public class Feeds {




    @NonNull
    @PrimaryKey
    private  String feed_id;

    @ColumnInfo(name = "profile_name")
    private String profileName;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "feed_image")
    private String feedImage;

    @ColumnInfo(name = "caption")
    private String caption;



    @ColumnInfo(name = "like")
    private Boolean like;

    @ColumnInfo(name = "bookmark")
    private Boolean bookmark;

    public Feeds(){
        //empty constructor needed for firebase database
    }


    public Feeds(String profileName, String location, String feedImage, String caption, Boolean like, Boolean bookmark) {

        this.profileName = profileName;
        this.location = location;
        this.feedImage = feedImage;
        this.caption = caption;
        this.like = like;
        this.bookmark = bookmark;
    }

    public String getProfileName() {
        return profileName;
    }


    public String getFeedImage() {
        return feedImage;
    }

    public void setFeedImage(String feedImage) {
        this.feedImage = feedImage;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    public Boolean getBookmark() {
        return bookmark;
    }

    public void setBookmark(Boolean bookmark) {
        this.bookmark = bookmark;
    }

    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }
}
