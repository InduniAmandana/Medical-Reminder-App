package com.s92075608.medical_app_s92075608;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MapsActivity extends AppCompatActivity {

    Button btnFindPharmacy, btnFindPark, btnFindGym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Buttons
        btnFindPharmacy = findViewById(R.id.btnFindPharmacy);
        btnFindPark = findViewById(R.id.btnFindPark);
        btnFindGym = findViewById(R.id.btnFindGym);

        // Open nearest pharmacy
        btnFindPharmacy.setOnClickListener(v ->
                openMapWithQuery("pharmacy"));

        // Open nearest park
        btnFindPark.setOnClickListener(v ->
                openMapWithQuery("park"));

        // Open nearest gym
        btnFindGym.setOnClickListener(v ->
                openMapWithQuery("gym"));
    }

    private void openMapWithQuery(String query) {
        // geo:0,0?q=<search term> opens nearby search
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + query);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        // If Google Maps app available
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            // Fallback: open Google Maps in browser
            Uri browserUri = Uri.parse("https://www.google.com/maps/search/" + query);
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, browserUri);
            startActivity(browserIntent);
        }
    }
}
