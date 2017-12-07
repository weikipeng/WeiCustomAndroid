package com.pengjunwei.android.editor.viewholder;

import android.view.View;

import com.pengjunwei.android.editor.model.WeiEditorVHBaseData;

/**
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorVHSpace extends WeiEditorVHBase<Object> {

    public WeiEditorVHSpace(View itemView, WeiEditorVHDelegate delegate) {
        super(itemView, delegate);

    }

    public void onBindViewHolder(int position, WeiEditorVHBaseData<Object> data) {
        super.onBindViewHolder(position, data);
        if (data == null) {
            return;
        }
    }
}
