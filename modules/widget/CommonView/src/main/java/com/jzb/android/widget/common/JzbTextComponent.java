package com.jzb.android.widget.common;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by wikipeng on 2018/1/19.
 */
public class JzbTextComponent {
    @NonNull
    protected Context  mContext;
    @NonNull
    protected TextView mCurrentView;

    protected int textColor;
    protected int textCheckedColor;

    protected boolean hasCustomColor        = false;
    protected boolean hasCustomCheckedColor = false;

    protected boolean isChecked;

    protected CharSequence mTextOn;
    protected CharSequence mTextOff;

    public JzbTextComponent(@NonNull Context context, AttributeSet attrs, @NonNull TextView currentView) {
        this.mContext = context;
        this.mCurrentView = currentView;

        TypedArray jzbAttrsTypedArray = context.obtainStyledAttributes(attrs, R.styleable.JzbTextAttrs);
        textColor = jzbAttrsTypedArray.getColor(R.styleable.JzbTextAttrs_Jzb_textColor, -1);
        textCheckedColor = jzbAttrsTypedArray.getColor(R.styleable.JzbTextAttrs_Jzb_textCheckedColor, -1);

        hasCustomColor = jzbAttrsTypedArray.hasValue(R.styleable.JzbTextAttrs_Jzb_textColor);
        hasCustomCheckedColor = jzbAttrsTypedArray.hasValue(R.styleable.JzbTextAttrs_Jzb_textCheckedColor);

        if (jzbAttrsTypedArray.hasValue(R.styleable.JzbTextAttrs_Jzb_textOn)) {
            mTextOn = jzbAttrsTypedArray.getText(R.styleable.JzbTextAttrs_Jzb_textOn);
        }

        if (jzbAttrsTypedArray.hasValue(R.styleable.JzbTextAttrs_Jzb_textOff)) {
            mTextOff = jzbAttrsTypedArray.getText(R.styleable.JzbTextAttrs_Jzb_textOff);
        }

        updateTextColor();

        jzbAttrsTypedArray.recycle();
    }

    public void updateTextColor() {
        if (hasCustomColor) {
            if (hasCustomCheckedColor) {
                ColorStateList colorStateList = createColorStateList(textColor, textCheckedColor);
                mCurrentView.setTextColor(colorStateList);
            } else {
                mCurrentView.setTextColor(textColor);
            }
        }
    }

    private ColorStateList createColorStateList(int defaultColor, int selectedColor) {
        final int[][] states = new int[2][];
        final int[]   colors = new int[2];
        int           i      = 0;

        states[i] = new int[]{android.R.attr.state_checked};
        colors[i] = selectedColor;
        i++;

        // Default enabled state
        states[i] = new int[]{};
        colors[i] = defaultColor;
        i++;

        return new ColorStateList(states, colors);
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
        syncTextState();
    }

    public void syncTextState() {
        boolean checked = isChecked();
        if (checked && mTextOn != null) {
            setText(mTextOn);
        } else if (!checked && mTextOff != null) {
            setText(mTextOff);
        }
    }

    public final void setText(CharSequence text) {
        mCurrentView.setText(text);
    }


    public boolean isChecked() {
        return isChecked;
    }
}
