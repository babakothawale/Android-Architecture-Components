package com.androidlearning.androidarchitecturecomponents.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.androidlearning.androidarchitecturecomponents.App;
import com.androidlearning.androidarchitecturecomponents.dao.ShopDao;
import com.androidlearning.androidarchitecturecomponents.entity.Shop;

@Database(entities = {Shop.class}, version = 2)
public abstract class SampleDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "roomsample.db";
    private static volatile SampleDatabase INSTANCE;

    public abstract ShopDao shopDao();

    public static SampleDatabase getInstance() {
        if (INSTANCE == null) {
            synchronized (SampleDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(App.getContext(), SampleDatabase.class, DATABASE_NAME)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
