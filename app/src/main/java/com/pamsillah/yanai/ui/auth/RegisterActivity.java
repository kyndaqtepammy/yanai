package com.pamsillah.yanai.ui.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pamsillah.yanai.MainActivity;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.accounts.AccountManager.KEY_PASSWORD;
import static com.facebook.internal.FacebookRequestErrorClassification.KEY_NAME;
import static com.pamsillah.yanai.config.Config.REGISTER_URL;

public class RegisterActivity extends AppCompatActivity {
    TextView mAlreadyHavAcc;
    EditText mEmail, mFullname, mUsername, mPassword, mPasswordAgain;
    Button mSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);

        mAlreadyHavAcc = findViewById(R.id.txt_go_to_signin);
        mEmail = findViewById(R.id.reg_email);
        mUsername = findViewById(R.id.reg_username);
        mFullname = findViewById(R.id.reg_name);
        mPassword = findViewById(R.id.reg_password);
        mPasswordAgain = findViewById(R.id.re_password);
        mSignUp = findViewById(R.id.btnRegister);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this, "Signing you up", "Please Wait...",
                false, false);

        final String name = mFullname.getText().toString().trim();
        final String email = mEmail.getText().toString().trim();
        final String password = mPassword.getText().toString().trim();
        final String username = mUsername.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(Config.TAG, response);
                // jsonObject = null;  //IDK
                try {
                    JSONObject  jsonObject = new JSONObject(response);
                    Boolean error = Boolean.parseBoolean(jsonObject.optString("error"));
                    String uid = jsonObject.optString("uid");
                    String name = jsonObject.optString("name");
                    String email = jsonObject.optString("email");
                    String message = jsonObject.optString("message");
                    
                    if ( !error ) {
                        //Creating a shared preference
                        SharedPreferences sharedPreferences = RegisterActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        //Creating editor to store values to shared preferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        //Adding values to editor
                        editor.putBoolean(Config.LOGGED_IN_PREF, true);
                        editor.putString(Config.USER_EMAIL, email);
                        editor.apply();

                        //  Saving values to editor
                        editor.apply();
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Log.d(Config.TAG, response);
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this, "An error ocuured. Please try again in a few minutes", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "An error ocuured. Please try again in a few minutes", Toast.LENGTH_SHORT).show();
                Log.d(Config.TAG, error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("password", password);
                params.put("username", username);
                params.put("email", email);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
}
