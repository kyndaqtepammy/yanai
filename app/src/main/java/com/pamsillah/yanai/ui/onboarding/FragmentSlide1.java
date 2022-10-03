package com.pamsillah.yanai.ui.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pamsillah.yanai.R;

public class FragmentSlide1 extends Fragment {
    TextView txtStep1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.onboard_screen1, container, false);
        txtStep1 = view.findViewById(R.id.txt_goTo2);

        txtStep1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }
}
