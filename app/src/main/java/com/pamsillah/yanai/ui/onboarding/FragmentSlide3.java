package com.pamsillah.yanai.ui.onboarding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pamsillah.yanai.MainActivity;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;

public class FragmentSlide3 extends Fragment {

    TextView txtStep1, txtStep2, txtFinish;
    RadioGroup rgLanguages;
    String languageString;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onboard_screen3, container, false);

        rgLanguages = view.findViewById(R.id.rg_lang);
        txtStep1 = view.findViewById(R.id.txt_step_1);
        txtFinish = view.findViewById(R.id.txt_finish);

        txtFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Taosted", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getContext().getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //TODO: sync subscriptions
                editor.putString(Config.LANGUAGE_PREF, languageString);
                editor.apply();
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        return view;
    }
}
