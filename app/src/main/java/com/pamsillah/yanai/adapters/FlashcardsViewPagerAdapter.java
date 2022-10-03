package com.pamsillah.yanai.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.ImageLoader;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;
import com.pamsillah.yanai.models.ModelFlashcards;
import com.pamsillah.yanai.utils.CustomVolleyRequest;
import com.pamsillah.yanai.utils.HelperMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.pamsillah.yanai.config.Config.LANGUAGE_NDEBELE;
import static com.pamsillah.yanai.config.Config.LANGUAGE_SHONA;

public class FlashcardsViewPagerAdapter extends PagerAdapter {
    // This holds all the currently displayable views, in order from left to right.
    private ArrayList<View> views = new ArrayList<View>();
    private Context context;
    private LayoutInflater layoutInflater;
    private List<ModelFlashcards> sliderImg;
    private ImageLoader imageLoader;

    public FlashcardsViewPagerAdapter(Context context, List<ModelFlashcards> sliderImg ) {
        this.context = context;
        this.sliderImg = sliderImg;
    }


    @Override
    public int getCount() {
        return sliderImg.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout_flashcard, null);
        ModelFlashcards utils = sliderImg.get(position);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView txtWord = view.findViewById(R.id.word);
        ImageView play_btn = view.findViewById(R.id.play_flash_audio);
        txtWord.setText(sliderImg.get(position).getFlashCardsTitle());
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(utils.getFlashCardImage(), ImageLoader.getImageListener(imageView, R.drawable.ic_replay_10_black_24dp, android.R.drawable.ic_dialog_alert));

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(HelperMethods.getLanguagePref(context.getApplicationContext())) {
                    case Config.LANGUAGE_NDEBELE:
                            Toast.makeText(context,sliderImg.get(position).getFlashCardNdebeleAudio() , Toast.LENGTH_LONG).show();
                            playAudio(sliderImg.get(position).getFlashCardNdebeleAudio());
                            Log.d(Config.TAG, sliderImg.get(position).getFlashCardNdebeleAudio());
                    case LANGUAGE_SHONA:
                        Toast.makeText(context,sliderImg.get(position).getFlashCardShonaAudio() , Toast.LENGTH_LONG).show();
                        playAudio(sliderImg.get(position).getFlashCardShonaAudio());
                        Log.d(Config.TAG, sliderImg.get(position).getFlashCardShonaAudio());
                    default:
                        Toast.makeText(context,sliderImg.get(position).getFlashCardNdebeleAudio() , Toast.LENGTH_LONG).show();
                        playAudio(sliderImg.get(position).getFlashCardNdebeleAudio());
                        Log.d(Config.TAG, sliderImg.get(position).getFlashCardNdebeleAudio());
                }
            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

    private void playAudio(String url) {
        MediaPlayer mediaPlayer;
        String audioUrl = url;

        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        Toast.makeText(context, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }

}
