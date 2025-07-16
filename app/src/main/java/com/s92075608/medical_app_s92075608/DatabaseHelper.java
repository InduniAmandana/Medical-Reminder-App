package com.s92075608.medical_app_s92075608;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Define the database name
    public static final String DATABASE_NAME = "signup.db";

    // Constructor for DatabaseHelper
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    // Method called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the allusers table with email as the primary key and password as a text field
        db.execSQL("CREATE TABLE allusers(email TEXT PRIMARY KEY, password TEXT)");
    }

    // Method called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the existing allusers table if it exists
        db.execSQL("DROP TABLE IF EXISTS allusers");
        // Recreate the table
        onCreate(db);
    }

    // Method to insert data into the allusers table
    public boolean insertData(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);

        // Insert the data and check if the insertion was successful
        long result = db.insert("allusers", null, contentValues);
        return result != -1; // Return true if insertion was successful, otherwise false
    }

    // Method to check if an email already exists in the allusers table
    public boolean checkEmail(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM allusers WHERE email = ?", new String[]{email});

        // Return true if email exists, otherwise false
        return cursor.getCount() > 0;
    }

    // Method to check if the email and password combination exists in the allusers table
    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM allusers WHERE email = ? AND password = ?", new String[]{email, password});

        // Return true if the combination exists, otherwise false
        return cursor.getCount() > 0;
    }
}
