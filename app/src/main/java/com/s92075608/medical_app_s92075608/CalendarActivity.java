package com.s92075608.medical_app_s92075608;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity { // <-- must be public

    private CalendarView calendarView;
    private TextView selectedDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar); // <-- fixed name (calendar not calender)

        calendarView = findViewById(R.id.calendarView);
        selectedDateText = findViewById(R.id.selectedDateText);

        // Set listener for date change
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String date = dayOfMonth + "/" + (month + 1) + "/" + year;
            selectedDateText.setText("Selected Date: " + date);
            Toast.makeText(CalendarActivity.this, "Date: " + date, Toast.LENGTH_SHORT).show();
        });
    }
}
