package com.s92075608.medical_app_s92075608;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivitywalkCount extends AppCompatActivity {
    private static final int SENSOR_CODE = 567; //used for requesting the activity recognition permission
    public static float STEPS = 0; //to store the number of steps
    private int[] upperBounds = {50, 100, 200, 250, 500, 1000, 2000, 2500, 3000, 5000, 10000}; //filling boundary
    ListView listView;
    TextView stepsView;
    TextView distanceInMetres;
    TextView caloriesBurnt;
    ProgressBar pBar;
    walkCountDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitywalk_count);

        // Check whether permission is already granted if not ask for it
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, SENSOR_CODE);
        }

        listView = findViewById(R.id.listView); // to display the walk count history
        stepsView = findViewById(R.id.stepsView); // to display the number of steps
        distanceInMetres = findViewById(R.id.inM); // to display the distance in meters
        caloriesBurnt = findViewById(R.id.calories); // to display the calories burnt
        pBar = findViewById(R.id.progress); // to represent the progress of steps

        db = new walkCountDatabase(this); // for handling database operations related to walk count

        listView.setAdapter(db.getMyListAdapter());

        Intent StepsIntent = new Intent(getApplicationContext(), StepListener.class);
        startService(StepsIntent);

        update(); // to update the displayed values periodically

        write(); // to write the walk count data to the database periodically
    }

    public void update() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateValues();

                //set steps to 0 at start of the day
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("IST"));
                String timeNow = sdf.format(new Date());
                if (timeNow.equals("00:00:00") || timeNow.equals("00:00:01") || timeNow.equals("00:00:02")) {
                    STEPS = 0;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    public void write() {
        final Handler writeHandler = new Handler();
        writeHandler.post(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("E,d MMM YYYY");
                sdf.setTimeZone(TimeZone.getTimeZone("IST"));
                String dateNow = sdf.format(new Date());

                db.writeTo(dateNow, (int) (STEPS * 0.762), (float) ((int) (STEPS * 0.762) * 0.76));

                writeHandler.postDelayed(this, 3600000);
            }
        });
    }

    public void updateValues() {
        stepsView.setText(STEPS + "\n STEPS");

        int m = (int) (STEPS * 0.762);
        distanceInMetres.setText(m + "\n Metres");

        float caloriesc = (float) (m * 0.76);
        caloriesBurnt.setText(caloriesc + "\n Calories Burnt");

        //Set it from array
        for (int i : upperBounds) {
            if (STEPS < i) {
                pBar.setMax(i);
                break;
            }
        }
        pBar.setProgress((int) STEPS);
    }

    @Override
    protected void onPause() {
        super.onPause();
        write();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        STEPS = 0;
    }
}
