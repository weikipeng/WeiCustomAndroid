package com.pengjunwei.android.editor.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.jzb.common.LogTool;


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
        LogTool.getInstance().s("onLayout%%%%%%%%%%%%%%%%%%%%%%%");
    }

    @Override
    public void addOnLayoutChangeListener(OnLayoutChangeListener listener) {
        super.addOnLayoutChangeListener(listener);
    }

    @Override
    protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        ///////////////////////////////////////////////////////////////////////////
        // 取消 某个子项失去焦点后 自动定位到 下个焦点的EditText
        // 例如 按Enter键后插入一行新的EditText 需要先关闭之前的EditText焦点，新的EditText获取焦点
        ///////////////////////////////////////////////////////////////////////////
        return true;
//        return super.onRequestFocusInDescendants(direction, previouslyFocusedRect);
    }
}
