package com.pengjunwei.android.custom.demo.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.pengjunwei.android.custom.demo.recyclerview.manager.WeiLinearLayoutManager;

/**
 * Created by wikipeng on 2017/12/15.
 */
public class WeiRecyclerView extends RecyclerView {
    public WeiRecyclerView(Context context) {
        super(context);
    }

    public WeiRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeiRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if (layout instanceof WeiLinearLayoutManager) {
            ((WeiLinearLayoutManager) layout).setRecyclerViewW(this);
        }
        super.setLayoutManager(layout);
    }
}
