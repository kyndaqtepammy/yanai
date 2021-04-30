package com.pamsillah.yanai.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.pamsillah.yanai.config.Config;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "yanai";
    public static final String BOOKS_TABLE = "books";
    public static final String COL1_BOOK_TITLE = "title";
    public static final String COL2_BOOK_AUTHOR = "author";
    public static final String COL3_BOOK_DESCR = "description";
    public static final String COL4_BOOK_COVER_URL = "cover_url";
    public static final String COL5_BOOK_PDF_URL = "pdf_url";
    public static final String COL6_BOOK_COVER_INTERNAL = "cover_internal";
    public static final String COL7_BOOK_PDF_INTERNAL = "pdf_internal";
    public static final String COL8_BOOK_DATE_ADDED = "date_added";
    public static final String COL9_BOOK_FAVORITED = "starred";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createBooksTable = "CREATE TABLE " + BOOKS_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL1_BOOK_TITLE + " TEXT," +
            COL2_BOOK_AUTHOR+ " TEXT," +
            COL3_BOOK_DESCR + " TEXT," +
            COL4_BOOK_COVER_URL + " TEXT," +
            COL5_BOOK_PDF_URL + " TEXT," +
            COL6_BOOK_COVER_INTERNAL + " TEXT," +
            COL7_BOOK_PDF_INTERNAL + " TEXT," +
            COL8_BOOK_DATE_ADDED + " TEXT,"+
            COL9_BOOK_FAVORITED + " TEXT)";

        db.execSQL(createBooksTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addBook( Context context,
        String title, String author, String descr,
        String cover_url, String pdf_url, String cover_internal,
        String pdf_internal, String date_added, String book_favorited
    ) {
        //check if book added
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM "+BOOKS_TABLE+ " WHERE "+COL5_BOOK_PDF_URL+" = "+"'"+pdf_url +"'";
        Cursor cursor = db.rawQuery(sql, null);
        ContentValues cv = new ContentValues();
        cv.put(COL1_BOOK_TITLE, title);
        cv.put(COL2_BOOK_AUTHOR, author);
        cv.put(COL3_BOOK_DESCR, descr);
        cv.put(COL4_BOOK_COVER_URL, cover_url);
        cv.put(COL5_BOOK_PDF_URL, pdf_url);
        cv.put(COL6_BOOK_COVER_INTERNAL, cover_internal);
        cv.put(COL7_BOOK_PDF_INTERNAL, pdf_internal);
        cv.put(COL8_BOOK_DATE_ADDED, date_added);
        cv.put(COL9_BOOK_FAVORITED, book_favorited);

        if (cursor.moveToFirst()) {
            //book exists
            ///Toast.makeText(context, "You already saved this book!", Toast.LENGTH_LONG).show();
        }
        else {
            Log.d(Config.TAG, "Adding") ;
        }
        long res = db.insertOrThrow(BOOKS_TABLE, null, cv);
       if (res == -1 ) {
           return false;
       }else {
           return true;
       }
    }


    public Cursor booksFromLocal() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + BOOKS_TABLE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
