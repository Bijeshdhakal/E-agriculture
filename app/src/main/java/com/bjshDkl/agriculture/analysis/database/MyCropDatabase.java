package com.bjshDkl.agriculture.analysis.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.bjshDkl.agriculture.analysis.model.CropDetailModel;
import com.bjshDkl.agriculture.utils.Constants;

import static androidx.room.Room.databaseBuilder;

@Database(entities = {CropDetailModel.class},version = 1)
public abstract class MyCropDatabase  extends RoomDatabase {
    public abstract MyDao myDao();

    private static MyCropDatabase mInstance;

    public static MyCropDatabase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context, MyCropDatabase.class, Constants.tableName)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

        }

        return mInstance;
    }
}
