package com.s92075608.medical_app_s92075608.java.com.s92075608.s92075608medirememberapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "medicines")
public class Medicine {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String type;
    public String dose;
    public int amount;            // e.g., number of tablets
    public long reminderAt;       // epoch millis (0 if not set)
    public boolean reminderEnabled;
}
