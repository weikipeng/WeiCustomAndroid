package com.pengjunwei.android.editor.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.pengjunwei.common.lib.LogTool;

/**
 * Created by wikipeng on 2017/12/8.
 */
public class WeiEditorRecyclerView extends RecyclerView{

    public WeiEditorRecyclerView(Context context) {
        super(context);
    }

    public WeiEditorRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WeiEditorRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        LogTool.getInstance().saveLog("onLayout%%%%%%%%%%%%%%%%%%%%%%%");
    }

    @Override
    public void addOnLayoutChangeListener(OnLayoutChangeListener listener) {
        super.addOnLayoutChangeListener(listener);
    }


}
