package com.s92075608.medical_app_s92075608;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ProfileActivity extends AppCompatActivity {

    private ImageView userImage;
    private EditText etUsername, etEmail, etDob;
    private Spinner spinnerGender;
    private Button btnSave, btnLogout;
    private TextView tvSaveMessage;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Init views
        userImage = findViewById(R.id.userImage);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etDob = findViewById(R.id.etDob);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnSave = findViewById(R.id.btnSave);
        btnLogout = findViewById(R.id.btnLogout);
        tvSaveMessage = findViewById(R.id.tvSaveMessage);

        prefs = getSharedPreferences("UserSession", MODE_PRIVATE);

        // Setup gender options
        String[] genders = {"Male", "Female", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, genders);
        spinnerGender.setAdapter(adapter);

        // Load saved data
        etUsername.setText(prefs.getString("username", "Induni Amandana"));
        etEmail.setText(prefs.getString("email", "gvia@gmail.com"));
        etDob.setText(prefs.getString("dob", "29 - July 2001"));

        String savedGender = prefs.getString("gender", "Female");
        int pos = adapter.getPosition(savedGender);
        spinnerGender.setSelection(pos);

        // Date picker for DOB
        etDob.setOnClickListener(v -> {
            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dp = new DatePickerDialog(ProfileActivity.this,
                    (DatePicker view, int y, int m, int d) -> {
                        etDob.setText(d + " - " + getMonthName(m) + " " + y);
                    }, year, month, day);
            dp.show();
        });

        // Save button
        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("username", etUsername.getText().toString());
            editor.putString("email", etEmail.getText().toString());
            editor.putString("dob", etDob.getText().toString());
            editor.putString("gender", spinnerGender.getSelectedItem().toString());
            editor.apply();

            // Show confirmation in layout
            tvSaveMessage.setVisibility(View.VISIBLE);
            tvSaveMessage.postDelayed(() ->
                    tvSaveMessage.setVisibility(View.GONE), 3000);

            Toast.makeText(ProfileActivity.this, "Profile saved", Toast.LENGTH_SHORT).show();
        });

        // Logout button
        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();
            Intent i = new Intent(ProfileActivity.this, SingupActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
        });
    }

    private String getMonthName(int month) {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return months[month];
    }
}
