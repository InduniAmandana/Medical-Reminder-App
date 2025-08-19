package com.s92075608.medical_app_s92075608.java.com.s92075608.s92075608medirememberapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MedicineDao {
    @Insert
    long insert(Medicine m);

    @Query("SELECT * FROM medicines ORDER BY reminderAt ASC")
    List<Medicine> getAll();

    @Delete
    void delete(Medicine m);
}
