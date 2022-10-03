package com.pamsillah.yanai.ui.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.android.volley.toolbox.JsonObjectRequest;
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
import com.facebook.login.widget.LoginButton;
import com.pamsillah.yanai.MainActivity;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;
import com.pamsillah.yanai.ui.onboarding.SlideActivity;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static com.facebook.appevents.UserDataStore.EMAIL;
import static com.pamsillah.yanai.config.Config.REGISTER_URL;
import static com.pamsillah.yanai.utils.HelperMethods.disconnectFromFacebook;


public class RegisterActivity extends AppCompatActivity {
    TextView mAlreadyHavAcc;
    EditText mEmail, mFullname, mUsername, mPassword/*, mPasswordAgain*/;
    Button mSignUp, mButtonFacebookReg;
    CallbackManager callbackManager;
    LoginManager loginManager;

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
        //mPasswordAgain = findViewById(R.id.re_password);
        mSignUp = findViewById(R.id.btnRegister);

//        mButtonFacebookReg = findViewById(R.id.button_facebook_reg);
//        FacebookSdk.sdkInitialize(RegisterActivity.this);
//        callbackManager = CallbackManager.Factory.create();
//        facebookLogin();

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

//        mButtonFacebookReg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginManager.logInWithReadPermissions(RegisterActivity.this, Arrays.asList("email", "public_profile", "user_birthday"));
//            }
//        });
    }

    private void signUp()
    {
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
                   // Boolean error = Boolean.parseBoolean(jsonObject.optString("error"));
                    //String uid = jsonObject.optString("uid");
                    Boolean success = Boolean.parseBoolean(jsonObject.optString("success"));
                    String email = jsonObject.optString("email");
                    String message = jsonObject.optString("message");
                    
                    if ( success ) {
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
                params.put("firstname", name);
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


    private void registerUser() {
        final ProgressDialog progressDialog = ProgressDialog.show(RegisterActivity.this, "Signing you in.", "Please Wait...", false);
        final String strUsername = mUsername.getText().toString().trim();
        final String strFullname = mFullname.getText().toString().trim();
        final String strUseremail= mEmail.getText().toString().trim();
        final String strPassword = mPassword.getText().toString().trim();

//        if (TextUtils.isEmpty(strUsername)) {
//            progressDialog.dismiss();
//            UtilMethods.shakeInputs(mLoginUsername, ActivityLogin.this);
//        }
//        if (TextUtils.isEmpty(strPassword)) {
//            progressDialog.dismiss();
//            UtilMethods.shakeInputs(mLoginPassword, ActivityLogin.this);
//        }else {
            RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
            JSONObject object = new JSONObject();
            try {
                object.put("name", strFullname);
                object.put("email", strUseremail);
                object.put("username", strUsername);
                object.put("password", strPassword);
            } catch (Exception e) {
                Log.d(Config.TAG, e.getMessage());
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    REGISTER_URL, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    progressDialog.dismiss();
                    Log.d(Config.TAG, response.toString());
                    try {
                        final Boolean success = Boolean.parseBoolean(response.getString("success"));
                        if ( success ) {
                            //go to welcome activity, set shared prefs
                            SharedPreferences sharedPreferences = RegisterActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putBoolean(Config.LOGGED_IN_PREF, true);
                            editor.putString(Config.KEY_USERNAME, strUsername);
                            editor.putString(Config.KEY_EMAIL, strUseremail);
                            editor.putString(Config.KEY_NAME, strFullname);
                            editor.putString(Config.KEY_PASSWORD, strPassword);
                            editor.apply();
                            Intent intent = new Intent(RegisterActivity.this, SlideActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            //progressDialog.dismiss();
                            startActivity(intent);
                            finish();
                        }else {
                            //snackbar here
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this, response.optString("message"), Toast.LENGTH_SHORT).show();
                            Log.d(Config.TAG, response.optString("message"));

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Log.d(Config.TAG, error.toString());

                }
            });
            requestQueue.add(jsonObjectRequest);
        //}
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

                                        SharedPreferences sharedPreferences = RegisterActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        //TODO: sync subscriptions
                                        editor.putBoolean(Config.LOGGED_IN_PREF, true);
                                        editor.putString(Config.USER_EMAIL, name);
                                        editor.putString(Config.USER_EMAIL, email);
                                        editor.putString(Config.USER_EMAIL, fbUserID);
                                        editor.apply();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
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
