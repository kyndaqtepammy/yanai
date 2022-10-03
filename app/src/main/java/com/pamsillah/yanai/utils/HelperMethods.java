package com.pamsillah.yanai.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.pamsillah.yanai.config.Config;

public class HelperMethods {
    public static boolean isUserSubscribed() {
        return true;
    }

    public static boolean isBookPaid(float bookPrice ) {
        return false;
    }

    public static void disconnectFromFacebook() {
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE,
                new GraphRequest
                        .Callback() {
                    @Override
                    public void onCompleted(GraphResponse graphResponse)
                    {
                        LoginManager.getInstance().logOut();
                    }
                })
                .executeAsync();
    }

    public static String getLanguagePref(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String languagePref = sp.getString(Config.LANGUAGE_PREF, "Ndebele");
        return languagePref;
    }
}
