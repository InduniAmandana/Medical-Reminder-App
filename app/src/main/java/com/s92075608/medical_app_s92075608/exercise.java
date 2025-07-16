package com.s92075608.medical_app_s92075608;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class exercise extends AppCompatActivity  {
    private Button buttonMap;
    private Button buttonWalk;
    private TextView textview;
    private SensorManager sensorManager;
    private Sensor tempSensor;
    private Boolean isTemperatureSensoreAvailable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);





        //button click into google map
        buttonMap = (Button) findViewById(R.id.buttonMap);
        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhelloooo();
            }
        });
        //button click into walking step counter section
        buttonWalk = (Button) findViewById(R.id.buttonWalk);
        buttonWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openhellooooo();
            }
        });
    }


    //linking for google map
    public void openhelloooo(){
        Intent intent = new Intent(this,MapsActivity.class);
        startActivity(intent);
    }


    //linking for walking step counter
    public void openhellooooo(){
        Intent intent = new Intent(this, MainActivitywalkCount.class);
        startActivity(intent);
    }


}