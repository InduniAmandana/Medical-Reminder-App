package com.s92075608.medical_app_s92075608;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashboardActivity extends AppCompatActivity {

    // Declare variables for UI components
    private Button btnAddMedicine;
    private TextView btnSignOut;
    private ImageView btnCalendar, btnNotifications, btnProfile;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // ===== Initialize UI elements with their IDs =====
        btnAddMedicine   = findViewById(R.id.btnAddMedicine);
        btnSignOut       = findViewById(R.id.btnSignOut);
        btnCalendar      = findViewById(R.id.btnCalendar);
        btnNotifications = findViewById(R.id.btnNotifications);
        btnProfile       = findViewById(R.id.btnProfile);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // ===== Top bar button actions =====
        // Go to AddMedicine screen
        btnAddMedicine.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, AddMedicineActivity.class)));

        // Open Calendar screen
        btnCalendar.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, CalendarActivity.class)));

        // Open Notifications screen
        btnNotifications.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, NotificationsActivity.class)));

        // Open Profile screen
        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class)));

        // ===== Sign out (back to Signup/Login) =====
        btnSignOut.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, SingupActivity.class);
            // Clear all previous activities so user can’t go back after logging out
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish(); // close Dashboard
        });

        // ===== Bottom navigation bar actions =====
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_walk) {           // Step counter page
                    goTo(MainActivitywalkCount.class);
                    return true;
                } else if (id == R.id.nav_maps) {    // Google Maps page
                    goTo(MapsActivity.class);
                    return true;
                } else if (id == R.id.nav_instructions) { // Instructions/Video page
                    goTo(InstructionActivity.class);
                    return true;
                } else if (id == R.id.nav_medicine) { // Medicine list page
                    goTo(MedicineListActivity.class);
                    return true;
                }
                return false;
            }
        });

        // Optional: prevent reloading the same activity if the same tab is tapped
        bottomNavigationView.setOnItemReselectedListener(item -> {});
    }

    // Helper method to reduce repeated code for starting new activities
    private void goTo(Class<?> cls) {
        Intent i = new Intent(this, cls);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT); // bring to front if already open
        startActivity(i);
    }
}
