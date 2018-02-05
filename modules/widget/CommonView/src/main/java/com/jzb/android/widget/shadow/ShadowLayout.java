package com.jzb.android.widget.shadow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;

import com.jzb.android.widget.common.R;

import static android.widget.FrameLayout.LayoutParams.UNSPECIFIED_GRAVITY;

/**
 * Created by wikipeng on 2018/1/25.
 */
public class ShadowLayout extends ViewGroup {
    private int DEFAULT_CHILD_GRAVITY = Gravity.TOP | Gravity.START;

    protected Drawable foregroundDrawable;
    protected boolean  foregroundDrawableBoundsChanged;
    protected int      paddingLeft;
    protected int      paddingRight;
    protected int      paddingTop;
    protected int      paddingBottom;

    public ShadowLayout(Context context) {
        super(context);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    //    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
    //        layoutChildren(left, top, right, bottom, false)
    //        if (changed)
    //            foregroundDrawBoundsChanged = changed
    //    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            foregroundDrawableBoundsChanged = true;
        }
    }

//    protected void layoutChildren(int left, int top, int right, int bottom, boolean forceLeftGravity) {
//        int count        = getChildCount();
//        int paddingLeft  = getPaddingLeftWithForeground();
//        int parentRight  = right - left - getPaddingRightWithForeground();
//        int parentTop    = getPaddingTopWithForeground();
//        int parentBottom = bottom - top - getPaddingBottomWithForeground();
//
//        View         child = null;
//        LayoutParams lp    = null;
//
//        int childLeft = 0;
//        int childTop  = 0;
//
//        for (int i = 0; i < count - 1; i++) {
//            child = getChildAt(i);
//            if (child.getVisibility() != View.GONE) {
//                lp = (LayoutParams) child.getLayoutParams();
//                int width  = child.getMeasuredWidth();
//                int height = child.getMeasuredHeight();
//
//                childLeft = 0;
//                childTop = 0;
//
//                int gravity = lp.gravity;
//                if (gravity == -1) {
//                    gravity = DEFAULT_CHILD_GRAVITY;
//                }
//
//                int layoutDirection = getLayoutDirection();
//                int absoluteGravity = Gravity.getAbsoluteGravity(gravity, layoutDirection);
//                int verticalGravity = gravity & Gravity.VERTICAL_GRAVITY_MASK;
//
//            }
//        }
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
//        ShapeUtils.roundedRect(shadowMarginLeft.toFloat(), shadowMarginTop.toFloat(), (w - shadowMarginRight).toFloat()
//                                    , (h - shadowMarginBottom).toFloat()
//                                    , cornerRadiusTL
//                                    , cornerRadiusTR
//                                    , cornerRadiusBR
//                                    , cornerRadiusBL)
    }

//    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
//
//        canvas?.let {
//            val w = measuredWidth
//            val h = measuredHeight
//            val path = ShapeUtils.roundedRect(shadowMarginLeft.toFloat(), shadowMarginTop.toFloat(), (w - shadowMarginRight).toFloat()
//                    , (h - shadowMarginBottom).toFloat()
//                    , cornerRadiusTL
//                    , cornerRadiusTR
//                    , cornerRadiusBR
//                    , cornerRadiusBL)
//            it.drawPath(path, bgPaint)
//        }
//    }


    //    private fun layoutChildren(left: Int, top: Int, right: Int, bottom: Int, forceLeftGravity: Boolean) {
    //        for (i in 0..(count - 1)) {
    //
    //            if (child.visibility != View.GONE) {
    //                val lp = child.layoutParams as LayoutParams
    //

    //
    //

    //
    //
    //                when (absoluteGravity and Gravity.HORIZONTAL_GRAVITY_MASK) {
    //                    Gravity.CENTER_HORIZONTAL -> childLeft = parentLeft + (parentRight - parentLeft - width) / 2 +
    //                            lp.leftMargin - lp.rightMargin + shadowMarginLeft - shadowMarginRight
    //                    Gravity.RIGHT -> {
    //                        if (!forceLeftGravity) {
    //                            childLeft = parentRight - width - lp.rightMargin - shadowMarginRight
    //                        }
    //                    }
    //                    Gravity.LEFT -> {
    //                        childLeft = parentLeft + lp.leftMargin + shadowMarginLeft
    //                    }
    //                    else -> childLeft = parentLeft + lp.leftMargin + shadowMarginLeft
    //                }
    //                when (verticalGravity) {
    //                    Gravity.TOP -> childTop = parentTop + lp.topMargin + shadowMarginTop
    //                    Gravity.CENTER_VERTICAL -> childTop = parentTop + (parentBottom - parentTop - height) / 2 +
    //                            lp.topMargin - lp.bottomMargin + shadowMarginTop - shadowMarginBottom
    //                    Gravity.BOTTOM -> childTop = parentBottom - height - lp.bottomMargin - shadowMarginBottom
    //                    else -> childTop = parentTop + lp.topMargin + shadowMarginTop
    //                }
    //                child.layout(childLeft, childTop, childLeft + width, childTop + height)
    //            }
    //        }
    //    }

    @Override
    public Drawable getForeground() {
        return foregroundDrawable;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        foregroundDrawableBoundsChanged = true;
    }


    public int getPaddingLeftWithForeground() {
        return paddingLeft;
    }

    public int getPaddingRightWithForeground() {
        return paddingRight;
    }

    public int getPaddingTopWithForeground() {
        return paddingTop;
    }

    public int getPaddingBottomWithForeground() {
        return paddingBottom;
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        int gravity = UNSPECIFIED_GRAVITY;

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.JzbLayout);
//            gravity = typedArray.getIndex(R.styleable.JzbView_Jzb_layout_gravity, UNSPECIFIED_GRAVITY);
            typedArray.recycle();
        }

        //        constructor(c: Context, attrs: AttributeSet?) : super(c, attrs) {
        //            val a = c.obtainStyledAttributes(attrs, R.styleable.ShadowView_Layout)
        //            gravity = a.getInt(R.styleable.ShadowView_Layout_layout_gravity, UNSPECIFIED_GRAVITY);
        //            a.recycle()
        //        }
        //
        //        constructor(source: ViewGroup.LayoutParams) : super(source)
        //
        //        companion object {
        //            val UNSPECIFIED_GRAVITY = -1
        //        }
    }
}
