package com.s92075608.medical_app_s92075608;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {

    private RecyclerView recyclerNotifications;
    private NotificationsAdapter adapter;
    private final List<String> notifications = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        recyclerNotifications = findViewById(R.id.recyclerNotifications);
        recyclerNotifications.setLayoutManager(new LinearLayoutManager(this));

        // Sample adapter (click = remove notification)
        adapter = new NotificationsAdapter(notifications, msg -> {
            notifications.remove(msg);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Dismissed: " + msg, Toast.LENGTH_SHORT).show();
        });

        recyclerNotifications.setAdapter(adapter);

        loadNotifications();
    }

    private void loadNotifications() {
        notifications.clear();
        // Dummy data (replace with real reminder notifications)
        notifications.add("Take 1 tablet of Paracetamol at 8:00 AM");
        notifications.add("Vitamin D supplement at 12:00 PM");
        notifications.add("Evening walk scheduled at 6:30 PM");

        adapter.notifyDataSetChanged();
    }
}
