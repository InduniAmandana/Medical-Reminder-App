package com.s92075608.s92075608medirememberapp.activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.s92075608.s92075608medirememberapp.R;

import butterknife.ButterKnife;

public class AlarmActivity extends BaseActivity {
    private static AlarmActivity inst;


    ImageView imageView;

    TextView title;

    TextView description;

    TextView timeAndData;

    Button closeButton;
    MediaPlayer mediaPlayer;

    public static AlarmActivity instance() {
        return inst;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        ButterKnife.bind(this); // Ensure ButterKnife binds the views

        // Starting notification sound
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.notification);
        mediaPlayer.start();

        // Display title, description, and date and time
        if (getIntent().getExtras() != null) {
            title.setText(getIntent().getStringExtra("TITLE"));
            description.setText(getIntent().getStringExtra("DESC"));
            timeAndData.setText(getIntent().getStringExtra("DATE") + ", " + getIntent().getStringExtra("TIME"));
        }

        // Load image from drawable to alarm notification box
        Glide.with(getApplicationContext()).load(R.drawable.alert).into(imageView);
        closeButton.setOnClickListener(view -> finish());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }
}
