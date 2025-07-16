package com.s92075608.medical_app_s92075608;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.widget.SimpleCursorAdapter;

//for creating and managing a SQLite database to store daily statistics for walking steps counter part
public class walkCountDatabase {
    //These are instance variables that store the Context object and SQLiteOpenHelper object
    Context context;
    //used to access the database.
    SQLiteOpenHelper helper;
    //walkCountDatabase(Context context) is the constructor of the walkCountDatabase class
    //that takes a Context object as input and initializes the helper object
    public walkCountDatabase(Context context){
        this.context=context;
        helper=new walkCountDatabaseHelper(context);
    }
    //takes three arguments to be written to the database.
    //Creates a new instance of the writeToDB class and executes it asynchronously using the AsyncTask class.
    // The writeToDB class defines the logic for writing data to the database
    public void writeTo(String date,int dist,float calorie){
        MyTaskParams params=new MyTaskParams(date,dist,calorie);
        new writeToDB().execute(params);
    }
    //returns a SimpleCursorAdapter object that can be used to populate a list view with the data from the db
    //creates a cursor by querying the table
    //maps the cursor data to the views in the layout file using the SimpleCursorAdapter class
    public SimpleCursorAdapter getMyListAdapter(){
        SQLiteDatabase db=helper.getReadableDatabase();
        Cursor cursor=db.query("STATISTICS",new String[]{"_id","DATE","DISTANCE","CALORIE"},null,null,null,null,"_id DESC");
        SimpleCursorAdapter adapter=new SimpleCursorAdapter(context,
                R.layout.view_list_singlewalkcount,
                cursor,
                new String[]{"DATE","DISTANCE","CALORIE"},
                new int[]{R.id.listDate,R.id.listDistance,R.id.listCalorie},
                0);

        return adapter;
    }
    //Parameters passed to the writeToDB class
    public static class MyTaskParams {
        String date;
        int dist;
        float calorie;

        MyTaskParams(String s, int i, float f) {
            this.date = s;
            this.dist = i;
            this.calorie = f;
        }
    }
//logic for writing data to the database.
//Takes an array of MyTaskParams objects as input, representing the data to be written.
    private class writeToDB extends AsyncTask<MyTaskParams,Void,Void>{
//opens a writable database,checks if the data already exists for the given date, and either inserts a new row or updates an existing row accordingly
        @Override
        protected Void doInBackground(MyTaskParams... myTaskParams) {
            String date=myTaskParams[0].date;
            int dist=myTaskParams[0].dist;
            float calorie=myTaskParams[0].calorie;
            SQLiteDatabase db=helper.getWritableDatabase();
            Cursor cursor=db.query("STATISTICS",new String[]{"DATE","DISTANCE","CALORIE"},null,null,null,null,null);
            cursor.moveToLast();
            if(cursor.getCount()==0||((cursor.getCount()>0)&&(!((cursor.getString(0)).equals(date))))) {

                ContentValues cv = new ContentValues();
                cv.put("DATE", date);
                cv.put("DISTANCE", dist);
                cv.put("CALORIE", calorie);
                db.insert("STATISTICS", null, cv);
            }else{
                int initialD=cursor.getInt(1);
                int d=initialD+dist;
                float initialC=cursor.getFloat(2);
                float c=initialC+calorie;
                ContentValues cv=new ContentValues();
                cv.put("DISTANCE",d);
                cv.put("CALORIE", c);
                db.update("STATISTICS",cv,"DATE=?",new String[]{date});
            }
            //Closes the database and returns null
            db.close();
            return null;
        }
    }

}
