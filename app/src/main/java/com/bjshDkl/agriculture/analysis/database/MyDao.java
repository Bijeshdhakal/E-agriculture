package com.bjshDkl.agriculture.analysis.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.bjshDkl.agriculture.analysis.model.CropDetailModel;
import com.bjshDkl.agriculture.utils.Constants;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MyDao {

    @Insert
    Long insertSample(CropDetailModel cropDetailModel);

    @Query("SELECT * FROM " + Constants.tableName)
    List<CropDetailModel> getCropDetails();

    @Query("DELETE FROM "+ Constants.tableName)
    void delete();
}
