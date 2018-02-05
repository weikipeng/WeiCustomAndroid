package com.jzb.android.widget.common;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by pengjunwei on 2018/1/13.
 */
public class JzbTextView extends AppCompatTextView {
    protected static final int[] CHECKED_STATE_SET = {
            android.R.attr.state_checked
    };
    protected JzbShapeComponent shapeComponent;
    protected JzbTextComponent  textComponent;
    protected boolean           mChecked;

    public JzbTextView(Context context) {
        this(context, null);
    }

    public JzbTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JzbTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        shapeComponent = new JzbShapeComponent(context, attrs, this);
        textComponent = new JzbTextComponent(context, attrs, this);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }

        return drawableState;
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
            textComponent.setChecked(checked);
            textComponent.syncTextState();
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        textComponent.updateTextColor();
        textComponent.syncTextState();
    }
    //
    //    private static ColorStateList createColorStateList(int defaultColor, int selectedColor) {
    //        final int[][] states = new int[2][];
    //        final int[] colors = new int[2];
    //        int i = 0;
    //
    //        states[i] = View.SELECTED_STATE_SET;
    //        colors[i] = selectedColor;
    //        i++;
    //
    //        // Default enabled state
    //        states[i] = View.EMPTY_STATE_SET;
    //        colors[i] = defaultColor;
    //        i++;
    //
    //        return new ColorStateList(states, colors);
    //    }
}
