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

    private Button btnAddMedicine;
    private TextView btnSignOut;
    private ImageView btnCalendar, btnNotifications, btnProfile;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // ===== Initialize UI =====
        btnAddMedicine   = findViewById(R.id.btnAddMedicine);
        btnSignOut       = findViewById(R.id.btnSignOut);
        btnCalendar      = findViewById(R.id.btnCalendar);
        btnNotifications = findViewById(R.id.btnNotifications);
        btnProfile       = findViewById(R.id.btnProfile);
        bottomNavigationView = findViewById(R.id.bottomNavigation);

        // ===== Top bar actions =====
        btnAddMedicine.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, AddMedicineActivity.class)));

        btnCalendar.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, CalendarActivity.class)));

        btnNotifications.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, NotificationsActivity.class)));

        btnProfile.setOnClickListener(v ->
                startActivity(new Intent(DashboardActivity.this, ProfileActivity.class)));

        // ===== Sign out (back to signup/login activity) =====
        btnSignOut.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, SingupActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        // ===== Bottom navigation actions =====
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_walk) {
                    goTo(MainActivitywalkCount.class);
                    return true;
                } else if (id == R.id.nav_maps) {
                    goTo(MapsActivity.class);
                    return true;
                } else if (id == R.id.nav_instructions) {
                    goTo(InstructionActivity.class);
                    return true;
                } else if (id == R.id.nav_medicine) {
                    goTo(MedicineListActivity.class);
                    return true;
                }
                return false;
            }
        });

        // Optional: Do nothing on reselection
        bottomNavigationView.setOnItemReselectedListener(item -> {});
    }

    private void goTo(Class<?> cls) {
        Intent i = new Intent(this, cls);
        i.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(i);
    }
}
