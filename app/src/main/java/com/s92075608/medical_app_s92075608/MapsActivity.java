package com.s92075608.medical_app_s92075608;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MapsActivity extends AppCompatActivity {

    EditText sourceEditText, destinationEditText;
    Button showMapButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        sourceEditText = findViewById(R.id.sourceEditText);
        destinationEditText = findViewById(R.id.destinationEditText);
        showMapButton = findViewById(R.id.showMapButton);

        showMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sourceAddress = sourceEditText.getText().toString();
                String destinationAddress = destinationEditText.getText().toString();

                // Launch Google Maps Activity with source and destination
                Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/dir/?api=1&origin=" +
                        sourceAddress + "&destination=" + destinationAddress);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }
}