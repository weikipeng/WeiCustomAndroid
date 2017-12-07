package com.pengjunwei.android.editor;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditor extends ConstraintLayout {
    public WeiEditor(Context context) {
        this(context,null);
    }

    public WeiEditor(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WeiEditor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public WeiEditor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        init(context, attrs, defStyleAttr);
//    }

    protected void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        View.inflate(context, R.layout.wei_editor_layout, this);
    }
}
