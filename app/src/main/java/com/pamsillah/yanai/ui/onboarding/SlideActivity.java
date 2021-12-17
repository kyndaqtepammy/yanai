package com.pamsillah.yanai.ui.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.pamsillah.yanai.R;
import com.pamsillah.yanai.adapters.SlideViewPagerAdapter;

public class SlideActivity extends AppCompatActivity {
    public static ViewPager viewPager;
    public SlideViewPagerAdapter slideViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide);

        viewPager = findViewById(R.id.viewpager);
        slideViewPagerAdapter = new SlideViewPagerAdapter(this);
        viewPager.setAdapter(slideViewPagerAdapter);
    }
}
