package com.pamsillah.yanai.custom.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener {
    public Activity activity;
    public Dialog dialog;
    public Button btnOK;
    public ImageView imgAlert;

    public CustomDialogClass(Activity a) {
        super(a);
        this.activity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_alert);
        btnOK = findViewById(R.id.btn_alert_yn);
        imgAlert = findViewById(R.id.img_alert_img);
        btnOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
