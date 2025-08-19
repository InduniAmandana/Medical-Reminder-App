package com.s92075608.medical_app_s92075608;

import android.Manifest;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.s92075608.medical_app_s92075608.java.com.s92075608.s92075608medirememberapp.AppDatabase;
import com.s92075608.medical_app_s92075608.java.com.s92075608.s92075608medirememberapp.Medicine;
import com.s92075608.medical_app_s92075608.java.com.s92075608.s92075608medirememberapp.MedicineDao;

import java.util.Calendar;

public class AddMedicineActivity extends AppCompatActivity {

    private EditText etName, etDose, etAmount;
    private Spinner spinnerType;
    private TextView tvDate, tvTime;
    private Switch switchReminder;
    private Button btnSave, btnViewAll;

    private Calendar calReminder; // holds selected date+time
    private MedicineDao dao;

    private static final String NOTIF_CHANNEL_ID = "med_channel";
    private static final int REQ_POST_NOTIF = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);

        dao = AppDatabase.getInstance(this).medicineDao();

        etName = findViewById(R.id.etName);
        etDose = findViewById(R.id.etDose);
        etAmount = findViewById(R.id.etAmount);
        spinnerType = findViewById(R.id.spinnerType);
        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        switchReminder = findViewById(R.id.switchReminder);
        btnSave = findViewById(R.id.btnSave);
        btnViewAll = findViewById(R.id.btnViewAll);

        // spinner data
        spinnerType.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Select Type", "Tablet", "Capsule", "Syrup", "Injection", "Drops"}));

        tvDate.setOnClickListener(v -> showDatePicker());
        tvTime.setOnClickListener(v -> showTimePicker());
        btnSave.setOnClickListener(v -> onSave());
        btnViewAll.setOnClickListener(v -> startActivity(new Intent(this, MedicineListActivity.class)));

        // Notification channel for Android O+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(
                    NOTIF_CHANNEL_ID, "Medicine Reminders", NotificationManager.IMPORTANCE_HIGH);
            getSystemService(NotificationManager.class).createNotificationChannel(ch);
        }

        // Request notification permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQ_POST_NOTIF);
            }
        }
    }

    private void showDatePicker() {
        Calendar now = Calendar.getInstance();
        new DatePickerDialog(this, (view, y, m, d) -> {
            if (calReminder == null) calReminder = Calendar.getInstance();
            calReminder.set(Calendar.YEAR, y);
            calReminder.set(Calendar.MONTH, m);
            calReminder.set(Calendar.DAY_OF_MONTH, d);
            tvDate.setText(String.format("%02d/%02d/%04d", d, m + 1, y));
        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        Calendar now = Calendar.getInstance();
        new TimePickerDialog(this, (view, h, min) -> {
            if (calReminder == null) calReminder = Calendar.getInstance();
            calReminder.set(Calendar.HOUR_OF_DAY, h);
            calReminder.set(Calendar.MINUTE, min);
            calReminder.set(Calendar.SECOND, 0);
            calReminder.set(Calendar.MILLISECOND, 0);
            tvTime.setText(String.format("%02d:%02d", h, min));
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), true).show();
    }

    private void onSave() {
        String name = etName.getText().toString().trim();
        String dose = etDose.getText().toString().trim();
        String amountStr = etAmount.getText().toString().trim();
        String type = spinnerType.getSelectedItem().toString();

        if (name.isEmpty() || dose.isEmpty() || amountStr.isEmpty() || "Select Type".equals(type)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Amount must be a number", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean reminderOn = switchReminder.isChecked();

        if (reminderOn) {
            if (calReminder == null) {
                Toast.makeText(this, "Pick reminder date & time", Toast.LENGTH_SHORT).show();
                return;
            }
            if (calReminder.getTimeInMillis() <= System.currentTimeMillis()) {
                Toast.makeText(this, "Reminder time is in the past", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        Medicine m = new Medicine();
        m.name = name;
        m.type = type;
        m.dose = dose;
        m.amount = amount;
        m.reminderEnabled = reminderOn;
        m.reminderAt = reminderOn ? calReminder.getTimeInMillis() : 0;

        long newId = dao.insert(m);
        m.id = (int) newId; // use id for unique PendingIntent

        if (reminderOn) {
            scheduleReminder(m.id, m.name, m.reminderAt);
        }

        Toast.makeText(this, "Saved (id: " + newId + ")", Toast.LENGTH_SHORT).show();

        // reset form
        etName.setText("");
        etDose.setText("");
        etAmount.setText("");
        spinnerType.setSelection(0);
        tvDate.setText("");
        tvTime.setText("");
        switchReminder.setChecked(true);
        calReminder = null;
    }

    private void scheduleReminder(int uniqueId, String medName, long whenMillis) {
        Intent intent = new Intent(this, ReminderReceiver.class);
        intent.putExtra("name", medName);

        PendingIntent pi = PendingIntent.getBroadcast(
                this,
                uniqueId, // unique per medicine
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, whenMillis, pi);
        } else {
            am.setExact(AlarmManager.RTC_WAKEUP, whenMillis, pi);
        }
    }
}
