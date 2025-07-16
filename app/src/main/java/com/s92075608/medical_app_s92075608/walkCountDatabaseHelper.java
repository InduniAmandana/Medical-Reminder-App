package com.s92075608.medical_app_s92075608;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//for writing and reading data from the SQLite database
public class walkCountDatabaseHelper extends SQLiteOpenHelper {
    //database name
    private static final String DB_NAME="DailyBase";
    //database version
    private static final int DB_VERSION=1;
    //walkCountDatabaseHelper(Context c)is the constructor of the walkCountDatabaseHelper class
    // that takes a Context object as input
    // The Context object is used to open or create the database
    walkCountDatabaseHelper(Context c){
        super(c,DB_NAME,null,DB_VERSION);
    }
    @Override
    //creating table in "DailyBase" database
    //Table named "STATISTICS" with four columns
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE STATISTICS("
        +"_id INTEGER PRIMARY KEY AUTOINCREMENT,"
        +"DATE TEXT,"
        +"DISTANCE INTEGER,"
        +"CALORIE INTEGER);");
    }
//Using this method when the database needs to be upgraded to a new version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
