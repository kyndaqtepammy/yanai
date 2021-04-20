package com.pamsillah.yanai.ui.pdf;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.pamsillah.yanai.R;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static com.pamsillah.yanai.config.Config.BOOK_PDF_URL;
import static com.pamsillah.yanai.config.Config.NODE_IMG_URL;

public class ActivityPDFReader extends AppCompatActivity {
    PDFView pdfView;
    String pdfurl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfreader);
        //init pdff view
        pdfView = findViewById(R.id.pdfView);
        Intent i = getIntent();
        pdfurl = i.getStringExtra(BOOK_PDF_URL);
        Toast.makeText(this, pdfurl, Toast.LENGTH_SHORT).show();
        new RetrivePDFfromUrl().execute(NODE_IMG_URL+pdfurl);

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
