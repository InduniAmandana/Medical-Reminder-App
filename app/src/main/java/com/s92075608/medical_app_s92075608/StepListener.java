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

// StepListener is a background Service that listens to step counter sensor events.
// It runs separately from the UI and updates the number of steps when the user walks.
public class StepListener extends Service implements SensorEventListener {

    public static float steps = 0; // global variable to store number of steps
    SensorManager sensorManager;   // used to access device sensors

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // This service is not bound to any activity, so return null
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Get the step counter sensor (hardware sensor)
        Sensor stepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        if (stepCounter != null) {
            // Try to register the sensor listener (fastest update mode + batch mode)
            final boolean batchMode = sensorManager.registerListener(
                    this,
                    stepCounter,
                    SensorManager.SENSOR_DELAY_FASTEST,
                    2000000); // batch delay in microseconds

            if (!batchMode) {
                // If registration failed, show error
                Log.e("BATCH_MODE", "Could not register batch mode for sensor");
                Toast.makeText(getApplicationContext(),
                        "Could not register batch mode for sensor",
                        Toast.LENGTH_SHORT).show();
            } else {
                // Success → sensor started
                Toast.makeText(this, "Started Counting Steps", Toast.LENGTH_LONG).show();
            }
        } else {
            // If the device does not support step counting
            Toast.makeText(this, "Device not Compatible!", Toast.LENGTH_LONG).show();
            this.stopSelf(); // stop the service
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // START_NOT_STICKY → service will not restart automatically if killed
        return START_NOT_STICKY;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Called when a new sensor event happens
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            steps++; // increment step count
            // Update the shared variable in MainActivitywalkCount
            MainActivitywalkCount.STEPS = steps;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used here, but required when implementing SensorEventListener
    }
}
