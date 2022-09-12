package com.pamsillah.yanai.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.adapters.AdapterBooks;
import com.pamsillah.yanai.config.Config;
import com.pamsillah.yanai.models.ModelBooks;
import com.pamsillah.yanai.models.ModelFlashcardCategories;
import com.pamsillah.yanai.ui.detailviews.BookviewFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.pamsillah.yanai.config.Config.BOOK_AUDIO;
import static com.pamsillah.yanai.config.Config.BOOK_AUTHOR;
import static com.pamsillah.yanai.config.Config.BOOK_DESCR;
import static com.pamsillah.yanai.config.Config.BOOK_ID;
import static com.pamsillah.yanai.config.Config.BOOK_IMAGE_URL;
import static com.pamsillah.yanai.config.Config.BOOK_PDF_URL;
import static com.pamsillah.yanai.config.Config.BOOK_PRICE;
import static com.pamsillah.yanai.config.Config.BOOK_RATING;
import static com.pamsillah.yanai.config.Config.BOOK_TITLE;
import static com.pamsillah.yanai.config.Config.FLASH_CATEGORIES;
import static com.pamsillah.yanai.config.Config.NODE_IMG_URL;
import static com.pamsillah.yanai.config.Config.TAG;

public class HomeFragment extends Fragment{

    List<ModelBooks> booksList;
    List<ModelFlashcardCategories> flashcatsList;
    RecyclerView mBooksRecycler, mFlashcardCatsRecycler;
    ModelBooks modelBooks;
    ModelFlashcardCategories mModelFlashcardsCats;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mBooksRecycler = root.findViewById(R.id.rv_fave_books);
        mBooksRecycler.setHasFixedSize(true);
        mBooksRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        booksList =  new ArrayList<>();

        //Flashcards setup
        mFlashcardCatsRecycler = root.findViewById(R.id.rv_flashcard_cats);
        mFlashcardCatsRecycler.setHasFixedSize(true);
        mFlashcardCatsRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        flashcatsList = new ArrayList<>();

        fetchFlashCats();
        //fetchBooks();
        return root;
    }

    private void fetchBooks() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.FETCH_BOOKS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Log.d(TAG, response);

                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray books = jsonObject.getJSONArray("books");

                    if(books.length() >0 ) {
                        for(int i=0; i<books.length(); i++) {
                            JSONObject booksObj = books.getJSONObject(i);
                            //Log.d(Config.TAG, booksObj.optString("id"));
                            modelBooks = new ModelBooks(
                              booksObj.optString("id"),
                              booksObj.optString("title"),
                              booksObj.optString("author"),
                              booksObj.optString("description"),
                              booksObj.optString("cover_url"),
                              booksObj.optString("pdf_url"),
                              booksObj.optString("date_added"),
                              booksObj.optString("price"),
                              booksObj.optString("audio_url")
                        );
                        booksList.add(modelBooks);
                        AdapterBooks adapterBooks = new AdapterBooks(getActivity(), booksList, new AdapterBooks.OnBookItemClickListener() {
                            @Override
                            public void onBookItemClick(int position) {
                                //Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
                                Bundle bundle = new Bundle();
                                bundle.putString(BOOK_ID, booksList.get(position).getBookID());
                                bundle.putString(BOOK_TITLE, booksList.get(position).getBookTitle());
                                bundle.putString(BOOK_AUTHOR, booksList.get(position).getBookAuthor() );
                                bundle.putString(BOOK_DESCR, booksList.get(position).getBookDescr() );
                                bundle.putString(BOOK_IMAGE_URL, booksList.get(position).getBookCoverUrl() );
                                bundle.putString(BOOK_PDF_URL, booksList.get(position).getBookPdfUrl());
                                bundle.putString(BOOK_RATING, "4" );
                                bundle.putString(BOOK_PRICE, booksList.get(position).getBookPrice());
                                bundle.putString(BOOK_AUDIO, booksList.get(position).getBookAudio());

                                Fragment bookFragment = new BookviewFragment();
                                bookFragment.setArguments(bundle);
                                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                                transaction.replace(R.id.nav_host_fragment, bookFragment ); // give your fragment container id in first parameter
                                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                                transaction.commit();
                            }
                        });
                        mBooksRecycler.setAdapter(adapterBooks);
                        adapterBooks.notifyDataSetChanged();
                        }
                    }
                }catch (Exception e) {
                    Log.d(Config.TAG, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(Config.TAG, error.toString());
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        // Show timeout error message
                        Toast.makeText(getContext(),
                                "Oops. Timeout error!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }

    private void fetchFlashCats() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Config.FLASH_CATEGORIES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
               // Log.d(TAG, response);

                try{
                    JSONArray flashCats = new JSONArray(response);

                    if(flashCats.length() >0 ) {
                        for(int i=0; i<flashCats.length(); i++) {
                            JSONObject obj = flashCats.getJSONObject(i);
                            Log.d(Config.TAG, FLASH_CATEGORIES);
                            mModelFlashcardsCats = new ModelFlashcardCategories(
                                    obj.optString("id"),
                                    obj.optString("name")
                            );
                            flashcatsList.add(mModelFlashcardsCats);
//                            AdapterBooks adapterBooks = new AdapterBooks(getActivity(), booksList, new AdapterBooks.OnBookItemClickListener() {
//                                @Override
//                                public void onBookItemClick(int position) {
//                                    //Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
//                                    Bundle bundle = new Bundle();
//                                    bundle.putString(BOOK_ID, booksList.get(position).getBookID());
//                                    bundle.putString(BOOK_TITLE, booksList.get(position).getBookTitle());
//                                    bundle.putString(BOOK_AUTHOR, booksList.get(position).getBookAuthor() );
//                                    bundle.putString(BOOK_DESCR, booksList.get(position).getBookDescr() );
//                                    bundle.putString(BOOK_IMAGE_URL, booksList.get(position).getBookCoverUrl() );
//                                    bundle.putString(BOOK_PDF_URL, booksList.get(position).getBookPdfUrl());
//                                    bundle.putString(BOOK_RATING, "4" );
//                                    bundle.putString(BOOK_PRICE, booksList.get(position).getBookPrice());
//                                    bundle.putString(BOOK_AUDIO, booksList.get(position).getBookAudio());
//
//                                    Fragment bookFragment = new BookviewFragment();
//                                    bookFragment.setArguments(bundle);
//                                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                                    transaction.replace(R.id.nav_host_fragment, bookFragment ); // give your fragment container id in first parameter
//                                    transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                                    transaction.commit();
//                                }
//                            });
//                            mBooksRecycler.setAdapter(adapterBooks);
//                            adapterBooks.notifyDataSetChanged();
                        }
                    }
                }catch (Exception e) {
                    Log.d(Config.TAG, e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Log.d(Config.TAG, error.toString());
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                if (error.networkResponse == null) {
                    if (error.getClass().equals(TimeoutError.class)) {
                        // Show timeout error message
                        Toast.makeText(getContext(),
                                "Oops. Timeout error!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.getCache().clear();
        requestQueue.add(stringRequest);
    }


}