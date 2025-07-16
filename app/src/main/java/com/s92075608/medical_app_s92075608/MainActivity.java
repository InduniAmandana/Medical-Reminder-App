package com.s92075608.medical_app_s92075608;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton medicalReminderButton = findViewById(R.id.medicalReminderButton);
        ImageButton exerciseButton = findViewById(R.id.exerciseButton);
        ImageButton profileButton = findViewById(R.id.profileButton);
        ImageButton healthInstructionButton = findViewById(R.id.healthInstructionButton);

        medicalReminderButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MediRemember.class);
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

        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileFragment.class);
            startActivity(intent);
        });


    }
}