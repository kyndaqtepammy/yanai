package com.pamsillah.yanai.config;

import android.media.MediaPlayer;

public class Config {
    public static final String TAG = "Hezvinoyi";
    public static final String BASE_URL = "http://192.0.1.96:8083/yanai";
    public static final String NODE_URL = "https://api.yanai.co.uk/wp-json/custom/v1";
    public static final String NODE_IMG_URL = "https://admin.yanai.co.uk/uploads/";
    public static final String FETCH_BOOKS = "https://api.yanai.co.uk/wp-json/wp/v2/books?acf_format=standard";
    public static final String FLASHCARDS_URL = "https://api.yanai.co.uk/wp-json/wp/v2/flashcard?acf_format=standard";
    public static final String LOGIN_URL = NODE_URL + "/login";
    public static final String REGISTER_URL = NODE_URL + "/register";
    public static final String FLASH_CATEGORIES = "https://api.yanai.co.uk/wp-json/wp/v2/flash_cats";
    public static final String SHARED_PREF_NAME = "yanaiprefs";
    public static final String LOGGED_IN_PREF = "loggedin";
    public static final String LANGUAGE_PREF = "language";
    public static final String USER_EMAIL = "userEmail";
    public static final String WELCOME_SCREEN_VIEWED = "viewed";
    //for the backend file keys
    public static final String KEY_NAME = "name";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PASSWORD = "password";
    //Book intents
    public static final String BOOK_PDF_URL = "pdf_url";
    public static final String BOOK_TITLE = "book_title";
    public static final String BOOK_AUTHOR = "book_author";
    public static final String BOOK_DESCR = "book_descr";
    public static final String BOOK_IMAGE_URL = "cover_url";
    public static final String BOOK_RATING = "book_rating";
    public static final String BOOK_ID = "book_id";
    public static final String BOOK_PRICE = "book_price";
    public static final String BOOK_AUDIO = "book_audio";
    public static final String READ_LOCAL_PDF_CODE = "1";
    public static final String PDFNMAE = "pdfname";
    public static final String CLICKED_FLASH_CATEGORY = "clicked_flashcat";

    //PAYPAL
    public static final String PAYPAL_CLIENT_ID = "AfnEiPAbeK7mwlfnGv2U7K3bqBrgX30fhtBuA9RHl4BREqg9jDJ9Yhneaw8W4tWnyfXaksBjCXGs5HsU";

    //PAYMENT PREFS
    public static final String PAYMENT_PREFS = "payment_prefs";
    public static final String LANGUAGE_SHONA = "Shona";
    public static final String LANGUAGE_NDEBELE = "Ndebele";

    //media player
    public static MediaPlayer MEDIA_PLAYER = new MediaPlayer();

}
