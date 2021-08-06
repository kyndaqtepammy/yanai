package com.pamsillah.yanai.payment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import java.math.BigDecimal;

public class ActivityMakePayment extends AppCompatActivity {
    Button mPaypalBtn;
    private int PAYPAL_REQ_CODE = 12;
    private static PayPalConfiguration payPalConfiguration = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) //TODO: CHANGE TO LIVE WHEN DONE
            .clientId(Config.PAYPAL_CLIENT_ID);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makepayment);
        mPaypalBtn = findViewById(R.id.btn_paypal);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
        startService(intent);

        mPaypalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paypalPaymentsMethod();
            }
        });
    }

    private void paypalPaymentsMethod() {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(3.99), "USD", "Yanai", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, com.paypal.android.sdk.payments.PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfiguration);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQ_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this, "Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
