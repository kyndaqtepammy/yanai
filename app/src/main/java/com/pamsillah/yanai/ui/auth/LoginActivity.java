package com.pamsillah.yanai.ui.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.material.textfield.TextInputEditText;
import com.pamsillah.yanai.MainActivity;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.pamsillah.yanai.config.Config.LOGIN_URL;
import static com.pamsillah.yanai.utils.HelperMethods.disconnectFromFacebook;

public class LoginActivity extends AppCompatActivity {
    TextView mGoToSignup;
    Button mLoginButton, mButtonFacebook;
    EditText mLoginEmail, mLoginPassword;
    private boolean loggedIn = false;
    CallbackManager callbackManager;
    LoginManager loginManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        mGoToSignup = findViewById(R.id.txt_go_to_signup);
        mLoginButton = findViewById(R.id.btnLogin);
        mLoginEmail = findViewById(R.id.login_email);
        mLoginPassword = findViewById(R.id.login_password);

        //mButtonFacebook = findViewById(R.id.button_facebook);
        //FacebookSdk.sdkInitialize(LoginActivity.this);
        //callbackManager = CallbackManager.Factory.create();
        //facebookLogin();
        getSupportActionBar().hide();

//        mButtonFacebook.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginManager.logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile", "user_birthday"));
//            }
//        });

        mLoginEmail.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setBackgroundDrawable(ContextCompat.getDrawable(LoginActivity.this, R.drawable.border_orange));
                return false;
            }
        });

        mLoginPassword.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setBackgroundDrawable(ContextCompat.getDrawable(LoginActivity.this, R.drawable.border_orange));
                return false;
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        mGoToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    public void shakeInputs(View view, Context context) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.border_rounded_red));
        } else {
            view.setBackground(ContextCompat.getDrawable(context, R.drawable.border_rounded_red));
        }
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation(shake);
    }

    private void login() {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Signing you in.", "Please Wait...", false);
        final String strEmail = mLoginEmail.getText().toString().trim( );
        final String strPassword = mLoginPassword.getText().toString().trim();


        if (TextUtils.isEmpty(strEmail)) {
            progressDialog.dismiss();
            shakeInputs(mLoginEmail, LoginActivity.this);
        }
        if (TextUtils.isEmpty(strPassword)) {
            progressDialog.dismiss();
            shakeInputs(mLoginPassword, LoginActivity.this);
        }else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    Log.d(Config.TAG, response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        Boolean error = Boolean.parseBoolean(obj.optString("error"));
                        String message = obj.optString("message");

                        if (!error) {
                            SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            //TODO: sync subscriptions
                            editor.putBoolean(Config.LOGGED_IN_PREF, true);
                            editor.putString(Config.USER_EMAIL, strEmail);
                            editor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        } else{
                            Toast.makeText(LoginActivity.this, "Message: "+message, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(LoginActivity.this, "Message: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    error.printStackTrace();
                    Log.d(Config.TAG, error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", strEmail);
                    params.put("password", strPassword);
                    return params;
                }
            };
            RequestQueue requestQueue  = Volley.newRequestQueue(LoginActivity.this);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(200 *1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGED_IN_PREF, false);

        //If we will get true
        if(loggedIn){
            //We will start the Profile Activity
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void facebookLogin() {
        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();

        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                if (object != null) {
                                    try {
                                        String name = object.getString("name");
                                        String email = object.getString("email");
                                        String fbUserID = object.getString("id");

                                        SharedPreferences sharedPreferences = LoginActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        //TODO: sync subscriptions
                                        editor.putBoolean(Config.LOGGED_IN_PREF, true);
                                        editor.putString(Config.USER_EMAIL, name);
                                        editor.putString(Config.USER_EMAIL, email);
                                        editor.putString(Config.USER_EMAIL, fbUserID);
                                        editor.apply();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();

                                        disconnectFromFacebook();

                                        // do action after Facebook login success
                                        // or call your API
                                    }
                                    catch (JSONException | NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                );

                Bundle parameters = new Bundle();
                parameters.putString(
                        "fields",
                        "id, name, email, gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // add this line
        callbackManager.onActivityResult(
                requestCode,
                resultCode,
                data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
