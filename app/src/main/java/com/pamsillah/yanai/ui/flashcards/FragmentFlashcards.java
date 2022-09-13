package com.pamsillah.yanai.ui.flashcards;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.pamsillah.yanai.R;
import com.pamsillah.yanai.adapters.FlashcardsViewPagerAdapter;
import com.pamsillah.yanai.models.ModelFlashcards;

import java.util.List;

//https://www.sanktips.com/2017/10/15/how-to-fetch-images-from-server-to-image-slider-with-viewpager-in-android-studio/
public class FragmentFlashcards extends Fragment {
    private ViewPager pager = null;
    private FlashcardsViewPagerAdapter pagerAdapter = null;
    RequestQueue rq;
    List<ModelFlashcards> sliderImg;
    String request_url = "http://localhost/sliderjsonoutput.php";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flashcards, container, false);
       // pagerAdapter = new FlashcardsViewPagerAdapter();
        pager = root.findViewById(R.id.flashcards_view_pager);
        pager.setAdapter(pagerAdapter);

        // Create an initial view to display; must be a subclass of FrameLayout.
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        FrameLayout v0 = (FrameLayout) layoutInflater.inflate (R.layout.one_of_my_page_layouts, null);
        pagerAdapter.addView (v0, 0);
        pagerAdapter.notifyDataSetChanged();
        return root;
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to add a view to the ViewPager.
    public void addView (View newPage)
    {
        int pageIndex = pagerAdapter.addView (newPage);
        // You might want to make "newPage" the currently displayed page:
        pager.setCurrentItem (pageIndex, true);
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to remove a view from the ViewPager.
    public void removeView (View defunctPage)
    {
        int pageIndex = pagerAdapter.removeView (pager, defunctPage);
        // You might want to choose what page to display, if the current page was "defunctPage".
        if (pageIndex == pagerAdapter.getCount())
            pageIndex--;
        pager.setCurrentItem (pageIndex);
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to get the currently displayed page.
    public View getCurrentPage ()
    {
        return pagerAdapter.getView (pager.getCurrentItem());
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to set the currently displayed page.  "pageToShow" must
    // currently be in the adapter, or this will crash.
    public void setCurrentPage (View pageToShow)
    {
        pager.setCurrentItem (pagerAdapter.getItemPosition (pageToShow), true);
    }
}
