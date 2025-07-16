package com.s92075608.medical_app_s92075608;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class instruction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        VideoView videoView1 = findViewById(R.id.videoView1);
        VideoView videoView2 = findViewById(R.id.videoView2);
        VideoView videoView3 = findViewById(R.id.videoView3);

        videoView1.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video1));
        videoView2.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video2));
        videoView2.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video3));

        MediaController mediaController1 = new MediaController(this);
        MediaController mediaController2 = new MediaController(this);
        MediaController mediaController3 = new MediaController(this);

        videoView1.setMediaController(mediaController1);
        videoView2.setMediaController(mediaController2);
        videoView3.setMediaController(mediaController3);
    }
}