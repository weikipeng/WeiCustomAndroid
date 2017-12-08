package com.pengjunwei.android.editor.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by wikipeng on 2017/12/8.
 */
public class WeiEditorLayoutManager extends LinearLayoutManager {

    public WeiEditorLayoutManager(Context context) {
        super(context);
    }

    public WeiEditorLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WeiEditorLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
