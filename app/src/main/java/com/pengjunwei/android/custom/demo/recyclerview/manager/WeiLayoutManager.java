package com.pengjunwei.android.custom.demo.recyclerview.manager;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by wikipeng on 2017/12/15.
 */
public class WeiLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
