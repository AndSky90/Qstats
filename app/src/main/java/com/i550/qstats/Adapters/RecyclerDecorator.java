package com.i550.qstats.Adapters;

import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class RecyclerDecorator extends RecyclerView.ItemDecoration {
    private int offset;

    public RecyclerDecorator(int offset) {
        this.offset = offset;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

        outRect.top = offset;
        outRect.bottom = offset;
        outRect.left = offset;
        outRect.right = offset;
    }
}

