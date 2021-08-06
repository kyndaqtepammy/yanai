package com.pamsillah.yanai.ui.detailviews;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.chibde.visualizer.LineBarVisualizer;
import com.facebook.internal.Logger;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;

import org.json.JSONArray;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.pamsillah.yanai.config.Config.BOOK_AUDIO;
import static com.pamsillah.yanai.config.Config.MEDIA_PLAYER;

public class ActivityPlayAudio extends AppCompatActivity {
    LineBarVisualizer lineBarVisualizer;
    ImageView mPlayButton, mPauseButton, mNextChapter, mPrevChapter, mSkip10s, mBack10s;
    int length;
    SeekBar skProgress;
    JSONArray songsList;
    Runnable mUpdateSeekbar;
    Handler mSeekbarUpdateHandler;
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);
        lineBarVisualizer = findViewById(R.id.visualizerLineBar);
        mPlayButton = findViewById(R.id.imgPlay);
        mPauseButton = findViewById(R.id.imgPause);
        skProgress = findViewById(R.id.seek);
        progressBar = findViewById(R.id.progress_bar_audio);


        //get audio intent
        Intent intent = getIntent();
        String audioUrl = intent.getStringExtra(BOOK_AUDIO);


        //autoplay
//        if ( MEDIA_PLAYER!= null && MEDIA_PLAYER.isPlaying()){
//            // +++Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();
//        }else{
//            playAudio();
//        }

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio();
            }
        });

        mPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPauseButton.setVisibility(View.INVISIBLE);
                mPlayButton.setVisibility(View.VISIBLE);
                length = MEDIA_PLAYER.getCurrentPosition();
                if ( MEDIA_PLAYER.isPlaying() ){
                    MEDIA_PLAYER.pause();
                    mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
                }
            }
        });

        //This handler will constantly update the progress
        mSeekbarUpdateHandler = new Handler();
        mUpdateSeekbar = new Runnable() {
            @Override
            public void run() {
                if ( MEDIA_PLAYER != null){
                    skProgress.setProgress(MEDIA_PLAYER.getCurrentPosition());
                }
                mSeekbarUpdateHandler.postDelayed(this, 50);
            }
        };
        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 1000);

        skProgress.setMax(MEDIA_PLAYER.getDuration());
        //change listener
        skProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean fromUser) {
                if (fromUser){
                    MEDIA_PLAYER.seekTo(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        MEDIA_PLAYER.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                //observer.stop();
//                progressBar.setProgress(mediaPlayer.getCurrentPosition());
//                MEDIA_PLAYER.stop();
//                MEDIA_PLAYER.reset();
            }
        });

        MEDIA_PLAYER.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                //show progressbar
                progressBar.setSecondaryProgress(percent);
                Log.d(Config.TAG, "buffering");
            }
        });

//        observer = new MediaObserver();
//        //MEDIA_PLAYER.prepareAsync();
//        MEDIA_PLAYER.start();
//        new Thread(observer).start();
    }


   // private MediaObserver observer = null;

//    private class MediaObserver implements Runnable {
//        private AtomicBoolean stop = new AtomicBoolean(false);
//
//        public void stop() {
//            stop.set(true);
//        }
//
//        @Override
//        public void run() {
//
//            while (!stop.get()) {
//                progressBar.setProgress((int)((double)MEDIA_PLAYER.getCurrentPosition() / (double)MEDIA_PLAYER.getDuration()*100));
//                try {
//                    Thread.sleep(200);
//                } catch (Exception ex) {
//                    //Logger.log(ToolS.this, ex);
//                }
//
//            }
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MEDIA_PLAYER.stop();
    }

    private void playAudio() {
        mPlayButton.setVisibility(View.INVISIBLE);
        mPauseButton.setVisibility(View.VISIBLE);
        MEDIA_PLAYER = new MediaPlayer();
        //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
        try {
            // MEDIA_PLAYER = new MediaPlayer();
            MEDIA_PLAYER.setDataSource("https://stephaniequeen.com/wp-content/uploads/2020/09/HeHasTroubleAudiobookSample.mp3");
            MEDIA_PLAYER.setAudioStreamType(AudioManager.STREAM_MUSIC);
            MEDIA_PLAYER.prepareAsync();
            MEDIA_PLAYER.seekTo(length);
            MEDIA_PLAYER.start();
            //visualizer
            int audioseshid = MEDIA_PLAYER.getAudioSessionId();
            if (audioseshid != -1){
                //mVisualiser.setAudioSessionId(audioseshid);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
