package com.jzb.android.widget.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pengjunwei on 2018/1/13.
 */
public class JzbShapeView extends View {
    protected JzbShapeComponent shapeComponent;

    public JzbShapeView(Context context) {
        this(context, null);
    }

    public JzbShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JzbShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        shapeComponent = new JzbShapeComponent(context, attrs, this);
    }
}
