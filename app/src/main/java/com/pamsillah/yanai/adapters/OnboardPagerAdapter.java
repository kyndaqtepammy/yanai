package com.pamsillah.yanai.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.pamsillah.yanai.MainActivity;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;
import com.pamsillah.yanai.models.ModelOnboard;
import com.pamsillah.yanai.ui.auth.LoginActivity;

public class OnboardPagerAdapter extends PagerAdapter {
    private Context mContext;
    String languageString;

    public OnboardPagerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ModelOnboard modelOnboard = ModelOnboard.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.activity_slide, container, false);

        TextView txtStep1, txtStep2, txtFinish;
        RadioGroup rgLanguages;
        rgLanguages = view.findViewById(R.id.rg_lang);
        txtStep1 = view.findViewById(R.id.txt_step_1);
        txtFinish = view.findViewById(R.id.txt_finish);

//        rgLanguages.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                switch (i){
//                    case R.id.lang_ndebele:
//                        languageString = Config.LANGUAGE_NDEBELE;
//                        break;
//                    case R.id.lang_shona:
//                        languageString = Config.LANGUAGE_SHONA;
//                        break;
//                    default:
//                        languageString = Config.LANGUAGE_NDEBELE;
//                }
//            }
//        });

        txtFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //TODO: sync subscriptions
                editor.putString(Config.LANGUAGE_PREF, languageString);
                editor.apply();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        ViewGroup layout = (ViewGroup) inflater.inflate(modelOnboard.getLayoutResId(), container, false);
        container.addView(layout);
        return layout;
    }

    @Override
    public int getCount() {
        return ModelOnboard.values().length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        ModelOnboard customPagerEnum = ModelOnboard.values()[position];
        return mContext.getString(customPagerEnum.getTitleResId());
    }
}
