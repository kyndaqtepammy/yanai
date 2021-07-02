package com.pamsillah.yanai.ui.detailviews;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pamsillah.yanai.MainActivity;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.config.Config;
import com.pamsillah.yanai.custom.dialogs.CustomDialogClass;
import com.pamsillah.yanai.ui.pdf.ActivityPDFReader;
import com.pamsillah.yanai.utils.DatabaseHelper;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    FloatingActionButton mDownload;
    LinearLayout linearLayout;
    DatabaseHelper databaseHelper;
    String date;
    ProgressDialog progressDialog;

    public BookviewFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_bookview, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        mBookTitle = root.findViewById(R.id.view_book_title);
        mBookAuthor = root.findViewById(R.id.view_book_author);
        mBookDescr  = root.findViewById(R.id.view_book_desr);
        mBookCover = root.findViewById(R.id.view_img_cover);
       linearLayout = root.findViewById(R.id.bookview_linear_top);
        mBookrating = root.findViewById(R.id.view_book_rating);
        mReadMore = root.findViewById(R.id.btn_read_more);
        mDownload = root.findViewById(R.id.fab_download);

        strBookID = getArguments().getString(BOOK_ID);
        strBookTitle = getArguments().getString(BOOK_TITLE);
        strBookAuthor = getArguments().getString(BOOK_AUTHOR);
        strBookDescr = getArguments().getString(BOOK_DESCR);
        strBookImgUrl = getArguments().getString(BOOK_IMAGE_URL);
        strBookRating = getArguments().getString(BOOK_RATING);
        strBookUrl = getArguments().getString(BOOK_PDF_URL);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        date = df.format(new Date());

        mBookTitle.setText(strBookTitle);
        mBookAuthor.setText(strBookAuthor);
        mBookDescr.setText(strBookDescr);
        Glide.with(getActivity()).asBitmap().load(NODE_IMG_URL+strBookImgUrl).into(mBookCover);
        Glide.with(getActivity())
                .asBitmap()
                .load(NODE_IMG_URL+strBookImgUrl)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        mBookCover.setImageBitmap(resource);
                        Palette.generateAsync(resource, new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette palette) {
                                // Do something with colors...
                                int dominant = palette.getDominantColor( 0x000000 );
                                int vibrant = palette.getVibrantColor(0x000000); // <=== color you want
                                int vibrantLight = palette.getLightVibrantColor(0x000000);
                                int vibrantDark = palette.getDarkVibrantColor(0x000000);
                                int muted = palette.getMutedColor(0x000000);
                                int mutedLight = palette.getLightMutedColor(0x000000);
                                int mutedDark = palette.getDarkMutedColor(0x000000);

                                linearLayout.setBackgroundColor(vibrantDark);
                            }
                        });
                    }
                });






        mReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readMore();
            }
        });
        mDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(getActivity(), "Saving your book.", "Please Wait...", false);
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        downloadBook();
                        handler.sendEmptyMessage(0);
                    }
                }).start();
            }
            Handler handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    progressDialog.dismiss();
                    CustomDialogClass customDialogClass = new CustomDialogClass(getActivity());
                    customDialogClass.show();
                    //show alert here

                }
            };
        });
        return root;
    }

    private void downloadBook() {
        String[] urls = {
            NODE_IMG_URL+strBookUrl,
            NODE_IMG_URL+strBookImgUrl
        };
        DownloadFileAsync downloadFileAsync = new DownloadFileAsync(urls);
        downloadFileAsync.execute(urls);
    }

    private boolean downloadBookS(final String path) {
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
            //save to SQLite here
            databaseHelper = new DatabaseHelper(getActivity());
            boolean bookAdded = databaseHelper.addBook(getActivity(), strBookTitle,
                    strBookAuthor, strBookDescr,
                    strBookImgUrl, strBookUrl,
                    "",
                    "",
                    date,
                    ""
            );
            if (bookAdded) {
                //progressDialog.dismiss();
            }
        } catch (Exception e ) {
           // progressDialog.dismiss();
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

    private class DownloadFileAsync extends AsyncTask<String, String, String> {
        int current=0;
        String[] paths;
        String fpath;
        boolean show = false;

        public DownloadFileAsync(String[] paths) {
            super();
            this.paths = paths;
            for (int i=0; i<paths.length;i++) {
                System.out.println((i+1)+": "+paths[i]);
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... aurl) {
            int rows = aurl.length;
            while(current < rows)
            {
                int count;
                try {
                    System.out.println("Current:  "+current+"\t\tRows: "+rows);
                    fpath = getFileName(this.paths[current]);
                    URL url = new URL(this.paths[current]);
                    URLConnection conexion = url.openConnection();
                    conexion.connect();
                    int lenghtOfFile = conexion.getContentLength();
                    InputStream input = new BufferedInputStream(url.openStream(), 512);
                    OutputStream output = new FileOutputStream(getActivity().getDir("books", Context.MODE_PRIVATE)+"/"+ fpath);
                    byte data[] = new byte[512];
                    long total = 0;
                    while ((count = input.read(data)) != -1) {
                        total += count;
                        publishProgress(""+(int)((total*100)/lenghtOfFile));
                        output.write(data, 0, count);
                    }
                    show = true;
                    output.flush();
                    output.close();
                    input.close();
                    //save to SQLite here
                    databaseHelper = new DatabaseHelper(getActivity());
                    boolean bookAdded = databaseHelper.addBook(getActivity(), strBookTitle,
                            strBookAuthor, strBookDescr,
                            strBookImgUrl, strBookUrl,
                            "",
                            "",
                            date,
                            ""
                    );
                    if (bookAdded) {
                        //progressDialog.dismiss();
                        //TODO: Success message here
                        Log.d(TAG, this.paths[current]);
                    } else {
                        //error message here
                    }
                    current++;
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
            }   //  while end
            return null;
        }

        public String getFileName(String wholePath)
        {
            String name=null;
            int start,end;
            start=wholePath.lastIndexOf('/');
            end=wholePath.length();     //lastIndexOf('.');
            name=wholePath.substring((start+1),end);
            //name = "books/"+name;
            System.out.println("Start:"+start+"\t\tEnd:"+end+"\t\tName:"+name);
            return name;
        }
    }
}


