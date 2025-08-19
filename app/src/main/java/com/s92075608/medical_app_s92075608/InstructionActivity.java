package com.s92075608.medical_app_s92075608;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.s92075608.medical_app_s92075608.R;

public class InstructionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);

        // Player 1
        YouTubePlayerView youTubePlayerView1 = findViewById(R.id.youtubePlayer1);
        getLifecycle().addObserver(youTubePlayerView1);
        youTubePlayerView1.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo("dQw4w9WgXcQ", 0); // Example: replace with your health video ID
            }
        });

        // Player 2
        YouTubePlayerView youTubePlayerView2 = findViewById(R.id.youtubePlayer2);
        getLifecycle().addObserver(youTubePlayerView2);
        youTubePlayerView2.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo("1sISguPDlhY", 0); // Correct video ID
            }
        });

        // Player 3
        YouTubePlayerView youTubePlayerView3 = findViewById(R.id.youtubePlayer3);
        getLifecycle().addObserver(youTubePlayerView3);
        youTubePlayerView3.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo("fN-NUpa_caE", 0); // Correct video ID
            }
        });
    }
}
