package com.jzb.android.widget.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wikipeng on 2018/1/19.
 */
public class JzbShapeComponent {
    public static final int SHAPE_TYPE_OVAL      = 1;
    public static final int SHAPE_TYPE_RECTANGLE = 2;
    @NonNull
    protected Context          mContext;
    @NonNull
    protected View             mCurrentView;
    protected int              mShapeType;
    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    protected int              mShapeColor;
    protected int              mShapeCheckedColor;
    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    protected int              mShapeBorderColor;
    protected int              mShapeBorderCheckedColor;
    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    protected int              mShapeBorderSize;
    protected float            mShapeCorner;
    protected GradientDrawable mShapeDrawable;

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////

    public JzbShapeComponent(@NonNull Context context, AttributeSet attrs, @NonNull View currentView) {
        this.mContext = context;
        this.mCurrentView = currentView;

        TypedArray jzbAttrsTypedArray = context.obtainStyledAttributes(attrs, R.styleable.JzbAttrs);
        mShapeType = jzbAttrsTypedArray.getInteger(R.styleable.JzbAttrs_Jzb_bgShape, -1);

        //--
        mShapeColor = jzbAttrsTypedArray.getColor(R.styleable.JzbAttrs_Jzb_bgShapeColor, -1);
        mShapeCheckedColor = jzbAttrsTypedArray.getColor(R.styleable.JzbAttrs_Jzb_bgShapeCheckedColor, -1);

        mShapeBorderColor = jzbAttrsTypedArray.getColor(R.styleable.JzbAttrs_Jzb_bgShapeBorderColor, -1);
        mShapeBorderCheckedColor = jzbAttrsTypedArray.getColor(R.styleable.JzbAttrs_Jzb_bgShapeBorderCheckedColor, -1);

        //--
        mShapeBorderSize = jzbAttrsTypedArray.getDimensionPixelSize(R.styleable.JzbAttrs_Jzb_bgShapeBorderSize, 0);

        mShapeCorner = jzbAttrsTypedArray.getDimensionPixelSize(R.styleable.JzbAttrs_Jzb_bgShapeCorner, 0);

        jzbAttrsTypedArray.recycle();

        updateBgShapeDrawable();
    }

    public void updateBgShapeDrawable() {
        if (mShapeType == -1 || mShapeColor == -1) {
            return;
        }

        if (mShapeBorderCheckedColor != -1) {
            ///////////////////////////////////////////////////////////////////////////
            // selector
            ///////////////////////////////////////////////////////////////////////////
            GradientDrawable shapeNormalDrawable  = createNormalShape();
            GradientDrawable shapeCheckedDrawable = createNormalShape();
            shapeCheckedDrawable.setColor(mShapeCheckedColor);
            shapeCheckedDrawable.setStroke(mShapeBorderSize, mShapeBorderCheckedColor);

            StateListDrawable stateListDrawable = new StateListDrawable();
            stateListDrawable.addState(new int[]{android.R.attr.state_checked}, shapeCheckedDrawable);
            stateListDrawable.addState(new int[]{}, shapeNormalDrawable);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mCurrentView.setBackground(stateListDrawable);
            } else {
                mCurrentView.setBackgroundDrawable(stateListDrawable);
            }
        } else {
            ///////////////////////////////////////////////////////////////////////////
            //
            ///////////////////////////////////////////////////////////////////////////
            //---
            mShapeDrawable = createNormalShape();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mCurrentView.setBackground(mShapeDrawable);
            } else {
                mCurrentView.setBackgroundDrawable(mShapeDrawable);
            }
        }
    }

    protected GradientDrawable createNormalShape() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        switch (mShapeType) {
            case SHAPE_TYPE_OVAL:
                gradientDrawable.setShape(GradientDrawable.OVAL);
                break;
            case SHAPE_TYPE_RECTANGLE:
                gradientDrawable.setShape(GradientDrawable.RECTANGLE);
                break;
        }

        gradientDrawable.setColor(mShapeColor);
        gradientDrawable.setStroke(mShapeBorderSize, mShapeBorderColor);
        gradientDrawable.setCornerRadius(mShapeCorner);

        return gradientDrawable;
    }
}
