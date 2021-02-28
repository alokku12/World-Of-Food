package com.alok.worldoffood.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.alok.worldoffood.room.dao.FeedDao;
import com.alok.worldoffood.room.entity.Feeds;

@Database(entities = {Feeds.class},version = 3)
public abstract class WorldOfFoodDatabase  extends RoomDatabase {


    public abstract FeedDao feedDao();


    private static volatile WorldOfFoodDatabase INSTANCE;

    public static WorldOfFoodDatabase getDatabaseInstance(Context context){




        if (INSTANCE == null) {
            synchronized (WorldOfFoodDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context,
                            WorldOfFoodDatabase.class, "world_of_food_database.db")
                            .fallbackToDestructiveMigration()
                            //to prepopulate the db if required
                            //.addCallback(sRoomDatabaseCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }
}
