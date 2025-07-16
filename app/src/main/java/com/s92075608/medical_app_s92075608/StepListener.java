package com.s92075608.medical_app_s92075608;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

//listens for step detector events from the device's sensors and counts the number of steps taken
public class StepListener extends Service implements SensorEventListener {
    public static float steps = 0;
    SensorManager sensorManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepCounter != null) {
            final boolean batchMode = sensorManager.registerListener(
                    this, stepCounter, SensorManager.SENSOR_DELAY_FASTEST, 2000000);
            if (!batchMode) {
                Log.e("BATCH_MODE", "Could not register batch mode for sensor");
                Toast.makeText(getApplicationContext(), "Could not register batch mode for sensor", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Started Counting Steps", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Device not Compatible!", Toast.LENGTH_LONG).show();
            this.stopSelf();
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            steps++;

            MainActivitywalkCount.STEPS = steps;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
