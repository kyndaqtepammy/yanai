package com.pamsillah.yanai.config;

public class Config {
    public static final String TAG = "Hezvinoyi";
    public static final String BASE_URL = "http://192.0.1.96:8083/yanai";
    public static final String NODE_URL = "http://192.0.1.96:5001/api";
    public static final String NODE_IMG_URL = "http://192.0.1.96:5001/uploads/";
    public static final String FETCH_BOOKS = NODE_URL + "/books";
    public static final String LOGIN_URL = BASE_URL + "/login.php";
    public static final String REGISTER_URL = BASE_URL + "/register.php";
    public static final String SHARED_PREF_NAME = "yanaiprefs";
    public static final String LOGGED_IN_PREF = "loggedin";
    public static final String USER_EMAIL = "userEmail";
    //for the backend file keys
    public static final String KEY_NAME = "name";
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


}
