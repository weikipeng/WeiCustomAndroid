package com.pengjunwei.android.editor.viewholder;

import android.support.annotation.CallSuper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.pengjunwei.android.editor.model.WeiEditorVHBaseData;
import com.pengjunwei.android.editor.model.WeiEditorVHStyle;

/**
 * Created by wikipeng on 2017/12/7.
 */
public abstract class WeiEditorVHBase<T> extends RecyclerView.ViewHolder {
    public WeiEditorVHBaseData<T>        mData;
    public WeiEditorVHDelegate delegate;

    public WeiEditorVHBase(View itemView, WeiEditorVHDelegate delegate) {
        super(itemView);
        this.delegate = delegate;
    }

    @CallSuper
    public void onBindViewHolder(int position, WeiEditorVHBaseData<T> data) {
        mData = data;
        updateViewStyle(itemView, mData.getStyle());
    }

    public void updateViewStyle(View view, WeiEditorVHStyle style){
        if (view == null) {
            return;
        }

        if (style == null) {
            return;
        }

        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (style.isNeedUpdate(WeiEditorVHStyle.FLAG_LAYOUT_PARAMS)) {
            layoutParams.width = style.getWidth();
            layoutParams.height = style.getHeight();
        }
    }
}
