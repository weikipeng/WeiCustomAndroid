package com.jzb.android.widget.recyclerview;

import android.support.v7.widget.RecyclerView;

public abstract class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {
    protected int mScrollThreshold;

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        boolean isSignificantDelta = Math.abs(dy) > mScrollThreshold;
        if (isSignificantDelta) {
            if (dy > 0) {
                onScrollUp();
            } else {
                onScrollDown();
            }
        }
    }

    public abstract void onScrollUp();

    public abstract void onScrollDown();

    public void setScrollThreshold(int scrollThreshold) {
        mScrollThreshold = scrollThreshold;
    }
}