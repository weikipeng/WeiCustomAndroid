package com.jzb.android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.RelativeLayout;

import com.jzb.android.widget.common.R;
import com.jzb.android.widget.shadow.ShapeUtils;

/**
 * Created by wikipeng on 2017/8/10.
 */
public class JzbRelativeLayout extends RelativeLayout {
    private int DEFAULT_CHILD_GRAVITY = Gravity.TOP | Gravity.START;

    public static final int SIZE_UNSET   = -1;
    public static final int SIZE_DEFAULT = 0;

    protected Drawable foregroundDraw;

    private boolean foregroundDrawBoundsChanged = false;
    private boolean foregroundDrawInPadding     = true;
    private int     foregroundDrawGravity       = Gravity.FILL;
    private   Rect selfBounds;
    private   Rect overlayBounds;
    protected int  foregroundColor;

    /**
     * 阴影颜色
     */
    protected int   shadowColor;
    protected float shadowRadius;
    protected float shadowDx;
    protected float shadowDy;

    //-
    protected Paint bgPaint;
    protected int   backgroundClr;

    protected int shadowMarginLeft;
    protected int shadowMarginTop;
    protected int shadowMarginRight;
    protected int shadowMarginBottom;

    //--
    protected float cornerRadiusTL;
    protected float cornerRadiusTR;
    protected float cornerRadiusBR;
    protected float cornerRadiusBL;

    public JzbRelativeLayout(Context context) {
        this(context, null);
    }

    public JzbRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JzbRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public JzbRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (context == null) {
            return;
        }

        selfBounds = new Rect();
        overlayBounds = new Rect();


        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowView, 0, 0);

        ///////////////////////////////////////////////////////////////////////////
        //
        ///////////////////////////////////////////////////////////////////////////
        shadowColor = a.getColor(R.styleable.ShadowView_shadowColor
                , ContextCompat.getColor(context, R.color.shadow_view_default_shadow_color));
        foregroundColor = a.getColor(R.styleable.ShadowView_foregroundColor
                , ContextCompat.getColor(context, R.color.shadow_view_foreground_color_dark));
        backgroundClr = a.getColor(R.styleable.ShadowView_backgroundColor, Color.WHITE);
        shadowDx = a.getFloat(R.styleable.ShadowView_shadowDx, 0f);
        shadowDy = a.getFloat(R.styleable.ShadowView_shadowDy, 1f);
        shadowRadius = a.getDimensionPixelSize(R.styleable.ShadowView_shadowRadius, SIZE_DEFAULT);
        Drawable d = a.getDrawable(R.styleable.ShadowView_android_foreground);
        if (d != null) {
            setForeground(d);
        }

        ///////////////////////////////////////////////////////////////////////////
        //
        ///////////////////////////////////////////////////////////////////////////
        int shadowMargin = a.getDimensionPixelSize(R.styleable.ShadowView_shadowMargin, SIZE_UNSET);
        if (shadowMargin >= 0) {
            shadowMarginTop = shadowMargin;
            shadowMarginLeft = shadowMargin;
            shadowMarginRight = shadowMargin;
            shadowMarginBottom = shadowMargin;
        } else {
            shadowMarginTop = a.getDimensionPixelSize(R.styleable.ShadowView_shadowMarginTop, SIZE_DEFAULT);
            shadowMarginLeft = a.getDimensionPixelSize(R.styleable.ShadowView_shadowMarginLeft, SIZE_DEFAULT);
            shadowMarginRight = a.getDimensionPixelSize(R.styleable.ShadowView_shadowMarginRight, SIZE_DEFAULT);
            shadowMarginBottom = a.getDimensionPixelSize(R.styleable.ShadowView_shadowMarginBottom, SIZE_DEFAULT);
        }

        ///////////////////////////////////////////////////////////////////////////
        //
        ///////////////////////////////////////////////////////////////////////////
        float cornerRadius = a.getDimensionPixelSize(R.styleable.ShadowView_cornerRadius, SIZE_UNSET);
        if (cornerRadius >= 0) {
            cornerRadiusTL = cornerRadius;
            cornerRadiusTR = cornerRadius;
            cornerRadiusBL = cornerRadius;
            cornerRadiusBR = cornerRadius;
        } else {
            cornerRadiusTL = a.getDimensionPixelSize(R.styleable.ShadowView_cornerRadiusTL, SIZE_DEFAULT);
            cornerRadiusTR = a.getDimensionPixelSize(R.styleable.ShadowView_cornerRadiusTR, SIZE_DEFAULT);
            cornerRadiusBL = a.getDimensionPixelSize(R.styleable.ShadowView_cornerRadiusBL, SIZE_DEFAULT);
            cornerRadiusBR = a.getDimensionPixelSize(R.styleable.ShadowView_cornerRadiusBR, SIZE_DEFAULT);
        }
        a.recycle();

        ///////////////////////////////////////////////////////////////////////////
        //
        ///////////////////////////////////////////////////////////////////////////
        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(backgroundClr);
        ///////////////////////////////////////////////////////////////////////////
        // 阴影
        ///////////////////////////////////////////////////////////////////////////
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        setWillNotDraw(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(null);
        } else {
            setBackgroundDrawable(null);
        }
        updatePaintShadow();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            foregroundDrawBoundsChanged = changed;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null) {
            return;
        }

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        Path path = ShapeUtils.INSTANCE.roundedRect(shadowMarginLeft, shadowMarginTop, (w - shadowMarginRight)
                , (h - shadowMarginBottom)
                , cornerRadiusTL
                , cornerRadiusTR
                , cornerRadiusBR
                , cornerRadiusBL);
        canvas.drawPath(path, bgPaint);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (canvas == null) {
            return;
        }

        canvas.save();
        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        Path path = ShapeUtils.INSTANCE.roundedRect(shadowMarginLeft, shadowMarginTop, (w - shadowMarginRight)
                , (h - shadowMarginBottom)
                , cornerRadiusTL
                , cornerRadiusTR
                , cornerRadiusBR
                , cornerRadiusBL);
        canvas.clipPath(path);
        drawForeground(canvas);
        canvas.restore();
    }

    protected void drawForeground(Canvas canvas) {
        if (foregroundDraw == null) {
            return;
        }
        if (foregroundDrawBoundsChanged) {
            foregroundDrawBoundsChanged = false;
            int w = getRight() - getLeft();
            int h = getBottom() - getTop();
            if (foregroundDrawInPadding) {
                selfBounds.set(0, 0, w, h);
            } else {
                selfBounds.set(getPaddingLeft(), getPaddingTop(),
                        w - getPaddingRight(), h - getPaddingBottom());
            }
            Gravity.apply(foregroundDrawGravity, foregroundDraw.getIntrinsicWidth(),
                    foregroundDraw.getIntrinsicHeight(), selfBounds, overlayBounds);
            foregroundDraw.setBounds(overlayBounds);
        }
        foregroundDraw.draw(canvas);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        foregroundDrawBoundsChanged = true;
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return super.verifyDrawable(who) || who == foregroundDraw;
    }

    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (foregroundDraw != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                foregroundDraw.setHotspot(x, y);
            }
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (foregroundDraw != null) {
            //            foregroundDraw?.takeIf { it.isStateful }?.let { it.state = drawableState }
            if (foregroundDraw.isStateful()) {
                foregroundDraw.setState(getDrawableState());
            }
        }
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (foregroundDraw != null) {
            foregroundDraw.jumpToCurrentState();
        }
    }


    @Override
    public void setForeground(Drawable drawable) {
        if (foregroundDraw != null) {
            foregroundDraw.setCallback(null);
            unscheduleDrawable(foregroundDraw);
        }

        foregroundDraw = drawable;

        updateForegroundColor();

        if (drawable != null) {
            setWillNotDraw(false);
            drawable.setCallback(this);
            if (drawable.isStateful()) {
                drawable.setState(getDrawableState());
            }
            if (foregroundDrawGravity == Gravity.FILL) {
                Rect padding = new Rect();
                drawable.getPadding(padding);
            }
        }
        requestLayout();
        invalidate();
    }

    @Override
    public Drawable getForeground() {
        return foregroundDraw;
    }

    private void updateForegroundColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (foregroundDraw instanceof RippleDrawable) {
                ((RippleDrawable) foregroundDraw).setColor(ColorStateList.valueOf(foregroundColor));
            }
        } else {
            foregroundDraw.setColorFilter(foregroundColor, PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public void setForegroundGravity(int foregroundGravity) {
        if (foregroundDrawGravity != foregroundGravity) {
            if ((foregroundGravity & Gravity.RELATIVE_HORIZONTAL_GRAVITY_MASK) == 0) {
                foregroundGravity = foregroundGravity | Gravity.START;
            }
            if ((foregroundGravity & Gravity.VERTICAL_GRAVITY_MASK) == 0) {
                foregroundGravity = foregroundGravity | Gravity.TOP;
            }
            foregroundDrawGravity = foregroundGravity;
            if (foregroundDrawGravity == Gravity.FILL && foregroundDraw != null) {
                Rect padding = new Rect();
                foregroundDraw.getPadding(padding);
            }
            requestLayout();
        }
    }

    @Override
    public int getForegroundGravity() {
        return foregroundDrawGravity;
    }

    public void updatePaintShadow(float radius, float dx, float dy, int shadowColor) {
        bgPaint.setShadowLayer(radius, dx, dy, shadowColor);
        invalidate();
    }

    private void updatePaintShadow() {
        updatePaintShadow(shadowRadius, shadowDx, shadowDy, shadowColor);
    }


    public void setShadowColor(int color) {
        this.shadowColor = color;
        updatePaintShadow();
    }
}
