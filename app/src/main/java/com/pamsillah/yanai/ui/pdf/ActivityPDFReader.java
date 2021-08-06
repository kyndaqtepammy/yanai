package com.pamsillah.yanai.ui.pdf;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.pamsillah.yanai.config.Config.BOOK_PDF_URL;
import static com.pamsillah.yanai.config.Config.NODE_IMG_URL;
import static com.pamsillah.yanai.config.Config.READ_LOCAL_PDF_CODE;

public class ActivityPDFReader extends AppCompatActivity {
    PDFView pdfView;
    String pdfurl, pdfname;
    //for from file
    File file;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfreader);
        //init pdff view
        pdfView = findViewById(R.id.pdfView);
        Intent i = getIntent();
        pdfname = i.getStringExtra(Config.PDFNMAE);
        pdfurl  = i.getStringExtra(BOOK_PDF_URL);

        if ( i.getBooleanExtra(READ_LOCAL_PDF_CODE, false) ) {
            readFromLocal();
        }

        new RetrivePDFfromUrl().execute(NODE_IMG_URL+pdfurl);
    }


    private void readFromLocal() {
        file = new File(ActivityPDFReader.this.getDir("books", Context.MODE_PRIVATE)+"/"+ pdfname);
        pdfView.recycle();
        pdfView.fromFile(file).load();
    }



    // create an async task class for loading pdf file from URL.
    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                //TODO: Change back to HttpsURLConnection when on live server
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();//(HttpsURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            pdfView.fromStream(inputStream).load();
        }
    }
}
