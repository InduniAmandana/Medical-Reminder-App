package com.s92075608.s92075608medirememberapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.s92075608.s92075608medirememberapp.activity.reminder;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton medicalReminderButton = findViewById(R.id.medicalReminderButton);
        ImageButton exerciseButton = findViewById(R.id.exerciseButton);
        ImageButton healthInstructionButton = findViewById(R.id.healthInstructionButton);

        medicalReminderButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, reminder.class);
            startActivity(intent);

        });

        exerciseButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, exercise.class);
            startActivity(intent);
        });

        healthInstructionButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, instruction.class);
            startActivity(intent);

        });

    }
}