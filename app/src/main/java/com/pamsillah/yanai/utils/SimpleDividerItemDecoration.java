package com.pamsillah.yanai.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.pamsillah.yanai.R;

public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
    private final int size;

    public SimpleDividerItemDecoration(int size) {
        this.size = size;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // Apply offset only to first item
        //if (parent.getChildAdapterPosition(view) == 0) {
            outRect.right += size;
        //}
    }


}
