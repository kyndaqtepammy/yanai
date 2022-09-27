package com.pamsillah.yanai.ui.flashcards;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.pamsillah.yanai.MainActivity;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.adapters.FlashcardsViewPagerAdapter;
import com.pamsillah.yanai.config.Config;
import com.pamsillah.yanai.models.ModelFlashcards;
import com.pamsillah.yanai.utils.CustomVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.pamsillah.yanai.config.Config.CLICKED_FLASH_CATEGORY;

//https://www.sanktips.com/2017/10/15/how-to-fetch-images-from-server-to-image-slider-with-viewpager-in-android-studio/
public class FragmentFlashcards extends Fragment {
    private ViewPager pager = null;
    private FlashcardsViewPagerAdapter pagerAdapter = null;
    RequestQueue rq;
    List<ModelFlashcards> sliderImg = new ArrayList<>();
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flashcards, container, false);
        String slug = getArguments().getString(CLICKED_FLASH_CATEGORY);
        //sliderDotspanel =  root.findViewById(R.id.SliderDots);
        pager = root.findViewById(R.id.flashcards_view_pager);
        pager.setAdapter(pagerAdapter);
        sendRequest(slug);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
//                for(int i = 0; i< dotscount; i++){
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.non_active_dot));
//                }
//                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_button));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        return root;
    }


    public void sendRequest(String slug){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "https://api.yanai.co.uk/wp-json/custom/v1/flashcards?slug="+slug, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d(Config.TAG, response.toString());
                for(int i = 0; i < response.length(); i++){
                    try {
                       // Log.d(Config.TAG, response.getJSONObject(i).getJSONArray("shona_word").toString());
                    ModelFlashcards  sliderUtils = new ModelFlashcards(
                            response.getJSONObject(i).optString("flashcard_image"),
                            response.getJSONObject(i).optString("title"),
                            response.getJSONObject(i).optString("id"),
                            response.getJSONObject(i).optString("english_audio"),
                            response.getJSONObject(i).optString("ndebele_audio"),
                            response.getJSONObject(i).optString("shona_audio"),
                            response.getJSONObject(i).getJSONArray("english_word").toString(),
                            response.getJSONObject(i).getJSONArray("ndebele_word").toString(),
                            response.getJSONObject(i).getJSONArray("shona_word").toString(),
                            response.getJSONObject(i).optString("slug"),
                            response.getJSONObject(i).optString("date_published")
                            );
                        sliderUtils.setFlashCardImage(sliderUtils.getFlashCardImage());
                        sliderImg.add(sliderUtils);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                pagerAdapter = new FlashcardsViewPagerAdapter(getActivity(), sliderImg);

                pager.setAdapter(pagerAdapter);
                dotscount = pagerAdapter.getCount();
                dots = new ImageView[dotscount];
                Log.d(Config.TAG, String.valueOf(dotscount));
//                for(int i = 0; i < dotscount; i++){
//                    dots[i] = new ImageView(getActivity());
//                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_border_primary));
//                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                    params.setMargins(8, 0, 8, 0);
//                    sliderDotspanel.addView(dots[i], params);
//                }
//               dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_button));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(Config.TAG, error.toString());
            }
        });
        CustomVolleyRequest.getInstance(getActivity()).addToRequestQueue(jsonArrayRequest);
    }
}

