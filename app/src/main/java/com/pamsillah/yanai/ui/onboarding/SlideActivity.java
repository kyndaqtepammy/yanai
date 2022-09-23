package com.pamsillah.yanai.ui.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.pamsillah.yanai.MainActivity;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.adapters.OnboardPagerAdapter;
import com.pamsillah.yanai.adapters.SlideViewPagerAdapter;
import com.pamsillah.yanai.config.Config;
import com.pamsillah.yanai.ui.auth.WelcomeActivity;
import com.ramotion.paperonboarding.PaperOnboardingFragment;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import java.util.ArrayList;

public class SlideActivity extends AppCompatActivity {
    private boolean welcomeScreenViewed = false;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_slide);
        viewPager = findViewById(R.id.onboard_viewpager);
        viewPager.setAdapter(new OnboardPagerAdapter(this));


    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        welcomeScreenViewed = sharedPreferences.getBoolean(Config.WELCOME_SCREEN_VIEWED, false);

        //If we will get true
        if(welcomeScreenViewed){
            //We will start the Profile Activity
            Intent intent = new Intent(SlideActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
