package com.pamsillah.yanai.adapters;

import android.content.Context;
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
import com.pamsillah.yanai.models.ModelFlashcards;
import com.pamsillah.yanai.utils.CustomVolleyRequest;

import java.util.ArrayList;
import java.util.List;

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
        txtWord.setText(sliderImg.get(position).getFlashCardsTitle());
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(utils.getFlashCardImage(), ImageLoader.getImageListener(imageView, R.drawable.ic_replay_10_black_24dp, android.R.drawable.ic_dialog_alert));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0){
                    Toast.makeText(context, String.valueOf(sliderImg.size()), Toast.LENGTH_SHORT).show();
                } else if(position == 1){
                    Toast.makeText(context, "Slide 2 Clicked", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Slide 3 Clicked", Toast.LENGTH_SHORT).show();
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

}
