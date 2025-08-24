package com.s92075608.medical_app_s92075608;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.s92075608.medical_app_s92075608.java.com.s92075608.s92075608medirememberapp.AppDatabase;
import com.s92075608.medical_app_s92075608.java.com.s92075608.s92075608medirememberapp.Medicine;
import com.s92075608.medical_app_s92075608.java.com.s92075608.s92075608medirememberapp.MedicineAdapter;
import com.s92075608.medical_app_s92075608.java.com.s92075608.s92075608medirememberapp.MedicineDao;

import java.util.ArrayList;
import java.util.List;

public class MedicineListActivity extends AppCompatActivity {

    // RecyclerView to show the list of medicines
    private RecyclerView recycler;
    // Adapter is used to connect medicine data with RecyclerView
    private MedicineAdapter adapter;
    // List of medicines (in memory)
    private final List<Medicine> items = new ArrayList<>();
    // DAO for database operations
    private MedicineDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        // Get the DAO object from AppDatabase
        dao = AppDatabase.getInstance(this).medicineDao();

        // Set up RecyclerView with vertical list layout
        recycler = findViewById(R.id.recyclerMedicines);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        // Create adapter with delete action
        adapter = new MedicineAdapter(items, m -> {
            // When user clicks delete → remove from database
            dao.delete(m);
            Toast.makeText(this, "Deleted: " + m.name, Toast.LENGTH_SHORT).show();
            // Reload list after deletion
            load();
        });

        // Attach adapter to RecyclerView
        recycler.setAdapter(adapter);
    }

    // Every time activity comes to foreground, reload medicines
    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    // Load all medicines from database into list
    private void load() {
        items.clear();              // remove old data
        items.addAll(dao.getAll()); // get new data from DB
        adapter.notifyDataSetChanged(); // tell adapter to refresh UI
    }
}
