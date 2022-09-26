package com.pamsillah.yanai.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pamsillah.yanai.ui.onboarding.FragmentSlide1;
import com.pamsillah.yanai.ui.onboarding.FragmentSlide2;
import com.pamsillah.yanai.ui.onboarding.FragmentSlide3;

public class SlideViewPagerAdapter extends FragmentPagerAdapter {

    public SlideViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return new FragmentSlide1();
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return new FragmentSlide2();
            case 2: // Fragment # 1 - This will show SecondFragment
                return new FragmentSlide3();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
