package com.pamsillah.yanai.ui.detailviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.ui.pdf.ActivityPDFReader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static com.pamsillah.yanai.config.Config.BOOK_AUTHOR;
import static com.pamsillah.yanai.config.Config.BOOK_DESCR;
import static com.pamsillah.yanai.config.Config.BOOK_ID;
import static com.pamsillah.yanai.config.Config.BOOK_IMAGE_URL;
import static com.pamsillah.yanai.config.Config.BOOK_PDF_URL;
import static com.pamsillah.yanai.config.Config.BOOK_RATING;
import static com.pamsillah.yanai.config.Config.BOOK_TITLE;
import static com.pamsillah.yanai.config.Config.NODE_IMG_URL;
import static com.pamsillah.yanai.config.Config.TAG;

public class BookviewFragment extends Fragment {
    TextView mBookTitle, mBookAuthor, mBookDescr;
    ImageView mBookCover;
    RatingBar mBookrating;
    Button mReadMore;
    String strBookID, strBookTitle, strBookAuthor, strBookDescr, strBookImgUrl, strBookRating, strBookUrl;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bookview, container, false);
        setHasOptionsMenu(true);
        mBookTitle = root.findViewById(R.id.view_book_title);
        mBookAuthor = root.findViewById(R.id.view_book_author);
        mBookDescr  = root.findViewById(R.id.view_book_desr);
        mBookCover = root.findViewById(R.id.view_img_cover);
        mBookrating = root.findViewById(R.id.view_book_rating);
        mReadMore = root.findViewById(R.id.btn_read_more);

        strBookID = getArguments().getString(BOOK_ID);
        strBookTitle = getArguments().getString(BOOK_TITLE);
        strBookAuthor = getArguments().getString(BOOK_AUTHOR);
        strBookDescr = getArguments().getString(BOOK_DESCR);
        strBookImgUrl = getArguments().getString(BOOK_IMAGE_URL);
        strBookRating = getArguments().getString(BOOK_RATING);
        strBookUrl = getArguments().getString(BOOK_PDF_URL);

        mBookTitle.setText(strBookTitle);
        mBookAuthor.setText(strBookAuthor);
        mBookDescr.setText(strBookDescr);
        Glide.with(getActivity()).load(NODE_IMG_URL+strBookImgUrl).into(mBookCover);

        mReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readMore();
            }
        });
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_with_download, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_download:
                downloadBook(NODE_IMG_URL+strBookUrl);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean downloadBook(final String path) {
        try {
            URL url = new URL(path);
            URLConnection ucon = url.openConnection();
            ucon.setReadTimeout(5000);
            ucon.setConnectTimeout(10000);
            InputStream is = ucon.getInputStream();
            BufferedInputStream inputStream = new BufferedInputStream(is, 1024 * 5);
            File file = new File(getActivity().getDir("filesdir", Context.MODE_PRIVATE)+ "/" + strBookTitle+".pdf");

            if (file.exists()) {
                Log.d(TAG, "FILE Exists");
                file.delete();
            }
            file.createNewFile();

            FileOutputStream outputStream = new FileOutputStream(file);
            byte[] buff =  new byte[5 * 1024];
            int len;
            while((len = inputStream.read(buff)) != -1 ) {
                outputStream.write(buff, 0, len);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (Exception e ) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void readMore() {
        Intent intent = new Intent(getActivity(), ActivityPDFReader.class);
        intent.putExtra(BOOK_PDF_URL, strBookUrl);
        startActivity(intent);
    }
}
