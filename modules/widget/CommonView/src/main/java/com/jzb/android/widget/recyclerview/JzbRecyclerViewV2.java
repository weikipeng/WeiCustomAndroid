package com.jzb.android.widget.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jzb.android.widget.common.JzbViewFocusManager;

/**
 * @since 6.8.4
 * Created by pengjunwei on 2018/1/10.
 */
public class JzbRecyclerViewV2 extends RecyclerView {
    protected JzbViewFocusManager mJzbViewFocusManager;

    public JzbRecyclerViewV2(Context context) {
        this(context, null);
    }

    public JzbRecyclerViewV2(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JzbRecyclerViewV2(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    public void init(Context context, @Nullable AttributeSet attrs, int defStyle) {
        mJzbViewFocusManager = new JzbViewFocusManager(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mJzbViewFocusManager != null) {
            mJzbViewFocusManager.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }
}
