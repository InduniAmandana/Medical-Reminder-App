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

    private RecyclerView recycler;
    private MedicineAdapter adapter;
    private final List<Medicine> items = new ArrayList<>();
    private MedicineDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);

        dao = AppDatabase.getInstance(this).medicineDao();

        recycler = findViewById(R.id.recyclerMedicines);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MedicineAdapter(items, m -> {
            dao.delete(m);
            Toast.makeText(this, "Deleted: " + m.name, Toast.LENGTH_SHORT).show();
            load();
        });
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        items.clear();
        items.addAll(dao.getAll());
        adapter.notifyDataSetChanged();
    }
}
