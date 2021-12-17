package com.pamsillah.yanai.ui.detailviews;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.analytics.AnalyticsListener;
import com.google.android.exoplayer2.audio.AudioListener;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;

import org.json.JSONArray;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.pamsillah.yanai.config.Config.BOOK_AUDIO;
import static com.pamsillah.yanai.config.Config.BOOK_AUTHOR;
import static com.pamsillah.yanai.config.Config.MEDIA_PLAYER;

public class ActivityPlayAudio extends AppCompatActivity implements ExoPlayer.EventListener {
    PlayerView playerView;
    BarVisualizer barVisualizer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);
        barVisualizer  = findViewById(R.id.blast);
        Intent i = getIntent();
        String strAudio = i.getStringExtra(BOOK_AUTHOR);
        Log.d(Config.TAG, Config.TAG+strAudio);

        initPlayer();

    }

    public void initPlayer(){
        playerView = findViewById(R.id.playerview);
        playerView.setControllerShowTimeoutMs(0);
        playerView.setCameraDistance(30);
        SimpleExoPlayer simpleExoPlayer = new SimpleExoPlayer.Builder(this).build();
        playerView.setPlayer(simpleExoPlayer);
        DataSource.Factory datasourcefactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "Yanai"));
        MediaSource audioSource = new ProgressiveMediaSource.Factory(datasourcefactory).createMediaSource(MediaItem.fromUri("https://stephaniequeen.com/wp-content/uploads/2020/09/HeHasTroubleAudiobookSample.mp3"));
        simpleExoPlayer.prepare(audioSource);
        simpleExoPlayer.setPlayWhenReady(true);

        simpleExoPlayer.addAudioListener(new AudioListener() {
            @Override
            public void onAudioSessionIdChanged(int audioSessionId) {
                if (audioSessionId !=  -1) {
                    barVisualizer.setAudioSessionId(audioSessionId);
                }
            }
        });
    }
}

