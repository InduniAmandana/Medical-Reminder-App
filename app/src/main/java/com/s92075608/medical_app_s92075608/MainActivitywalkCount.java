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

    private static final int SENSOR_CODE = 567; // request code for activity recognition permission
    public static float STEPS = 0;              // global variable to store steps
    // Step milestones to set progress bar limits dynamically
    private int[] upperBounds = {50, 100, 200, 250, 500, 1000, 2000, 2500, 3000, 5000, 10000};

    // UI elements
    ListView listView;
    TextView stepsView;
    TextView distanceInMetres;
    TextView caloriesBurnt;
    ProgressBar pBar;

    // Database for saving walk count history
    walkCountDatabase db;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activitywalk_count);

        // ===== Permission check for step counter =====
        // If permission is not already granted, ask the user
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
                == PackageManager.PERMISSION_DENIED) {
            requestPermissions(new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, SENSOR_CODE);
        }

        // ===== Connect UI components with XML =====
        listView       = findViewById(R.id.listView);       // shows past step history
        stepsView      = findViewById(R.id.stepsView);      // shows steps count
        distanceInMetres = findViewById(R.id.inM);          // shows distance covered
        caloriesBurnt  = findViewById(R.id.calories);       // shows calories burnt
        pBar           = findViewById(R.id.progress);       // progress of steps

        db = new walkCountDatabase(this); // create database object

        // set adapter to show stored records in listView
        listView.setAdapter(db.getMyListAdapter());

        // start background service that listens to steps
        Intent StepsIntent = new Intent(getApplicationContext(), StepListener.class);
        startService(StepsIntent);

        // update screen every second
        update();

        // write steps to database every 1 hour
        write();
    }

    // ===== Update steps, distance and calories in real-time =====
    public void update() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateValues();

                // reset steps at midnight
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("IST"));
                String timeNow = sdf.format(new Date());
                if (timeNow.equals("00:00:00") || timeNow.equals("00:00:01") || timeNow.equals("00:00:02")) {
                    STEPS = 0;
                }
                handler.postDelayed(this, 1000); // repeat every 1 second
            }
        });
    }

    // ===== Write step data to DB every hour =====
    public void write() {
        final Handler writeHandler = new Handler();
        writeHandler.post(new Runnable() {
            @Override
            public void run() {
                // get current date
                SimpleDateFormat sdf = new SimpleDateFormat("E,d MMM YYYY");
                sdf.setTimeZone(TimeZone.getTimeZone("IST"));
                String dateNow = sdf.format(new Date());

                // save distance (steps * 0.762) and calories (distance * 0.76)
                db.writeTo(dateNow, (int) (STEPS * 0.762), (float) ((int) (STEPS * 0.762) * 0.76));

                writeHandler.postDelayed(this, 3600000); // run every hour
            }
        });
    }

    // ===== Calculate and update values on UI =====
    public void updateValues() {
        stepsView.setText(STEPS + "\n STEPS");

        int m = (int) (STEPS * 0.762); // 1 step ≈ 0.762 metres
        distanceInMetres.setText(m + "\n Metres");

        float caloriesc = (float) (m * 0.76); // calories burnt estimation
        caloriesBurnt.setText(caloriesc + "\n Calories Burnt");

        // set progress bar max value based on milestones
        for (int i : upperBounds) {
            if (STEPS < i) {
                pBar.setMax(i);
                break;
            }
        }
        pBar.setProgress((int) STEPS);
    }

    // ===== Save data when app goes to background =====
    @Override
    protected void onPause() {
        super.onPause();
        write();
    }

    // ===== Reset steps when app is closed =====
    @Override
    protected void onDestroy() {
        super.onDestroy();
        STEPS = 0;
    }
}
