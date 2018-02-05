package com.jzb.android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.jzb.android.widget.common.JzbShapeComponent;
import com.jzb.android.widget.common.JzbViewFocusManager;
import com.jzb.android.widget.common.R;

/**
 * Created by wikipeng on 2017/8/10.
 */
public class JzbRelativeLayout extends RelativeLayout {
    protected Paint    mPaint;
    protected Drawable mForegroundSelector;

    /**
     * 圆角角度
     */
    protected float mCornerRadius;

    ///////////////////////////////////////////////////////////////////////////
    // 阴影
    ///////////////////////////////////////////////////////////////////////////
    protected Rect    mShadowRect;
    protected RectF   mShadowRectF;
    /**
     * 阴影颜色
     */
    @ColorInt
    protected int     mShadowColor;
    /**
     * 阴影大小
     */
    protected int     mShadowSize;
    /**
     * 阴影是否渐变
     */
    protected boolean isShadowGradient;

    protected JzbViewFocusManager mJzbViewFocusManager;

    //    protected GradientDrawable mShadowLayers[];
    //    protected LayerDrawable    mShadowLayerDrawable;

    ///////////////////////////////////////////////////////////////////////////
    // 6.8.4
    ///////////////////////////////////////////////////////////////////////////
    protected JzbShapeComponent jzbShapeComponent;

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

    private void init(Context context, AttributeSet attrs) {
        jzbShapeComponent = new JzbShapeComponent(context, attrs, this);

        TypedArray typedArray       = context.obtainStyledAttributes(attrs, new int[]{android.R.attr.foreground});
        TypedArray shadowTypedArray = context.obtainStyledAttributes(attrs, R.styleable.JzbView);

        mForegroundSelector = typedArray.getDrawable(0);

        mShadowColor = shadowTypedArray.getColor(R.styleable.JzbView_shadowColor, Color.TRANSPARENT);
        mShadowSize = shadowTypedArray.getDimensionPixelSize(R.styleable.JzbView_shadowSize, 0);
        isShadowGradient = shadowTypedArray.getBoolean(R.styleable.JzbView_isShadowGradient, false);
        mCornerRadius = shadowTypedArray.getDimensionPixelSize(R.styleable.JzbView_cornerRadius, 0);
        mJzbViewFocusManager = new JzbViewFocusManager(context, attrs);

        ///////////////////////////////////////////////////////////////////////////
        // 前景色角度
        ///////////////////////////////////////////////////////////////////////////
        updateForeGroundDrawable();

        ///////////////////////////////////////////////////////////////////////////
        //
        ///////////////////////////////////////////////////////////////////////////

        shadowTypedArray.recycle();
        typedArray.recycle();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ///////////////////////////////////////////////////////////////////////////
        // 阴影
        ///////////////////////////////////////////////////////////////////////////

        if (mShadowSize > 0) {
            mShadowRect = new Rect();
            mShadowRectF = new RectF();
        }
    }

    /**
     * 更新前景色配置
     */
    protected void updateForeGroundDrawable() {
        //set a callback, or the selector won't be animated
        if (mForegroundSelector != null) {
            mForegroundSelector.setCallback(this);
            //LogTrackTool.getInstance().t("jzbFocus debug mForegroundSelector class is " + mForegroundSelector);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
                    && mForegroundSelector instanceof RippleDrawable) {
                RippleDrawable rippleDrawable = (RippleDrawable) mForegroundSelector;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    rippleDrawable.setRadius((int) mCornerRadius);
                } else {
                    int      numberOfLayers      = rippleDrawable.getNumberOfLayers();
                    Drawable tRippleDrawableItem = null;
                    for (int i = 0; i < numberOfLayers; i++) {
                        tRippleDrawableItem = rippleDrawable.getDrawable(i);
                        //LogTrackTool.getInstance().t("jzbFocus debug rippleDrawableCurrent class is " + tRippleDrawableItem);
                        if (tRippleDrawableItem instanceof ColorDrawable) {
                            ColorDrawable colorDrawable = (ColorDrawable) tRippleDrawableItem;
                            //                            GradientDrawable gradientDrawable = new GradientDrawable();
                            //                            gradientDrawable.setColor(colorDrawable.getColor());
                            //                            rippleDrawable.setd(i,gradientDrawable);
                        } else if (tRippleDrawableItem instanceof GradientDrawable) {
                            GradientDrawable gradientDrawable = (GradientDrawable) tRippleDrawableItem;
                            gradientDrawable.setCornerRadius(mCornerRadius);
                        }
                    }
                }
            } else if (mForegroundSelector instanceof StateListDrawable) {
                StateListDrawable stateListDrawable = (StateListDrawable) mForegroundSelector;
                DrawableContainer.DrawableContainerState drawableContainerState
                        = (DrawableContainer.DrawableContainerState) stateListDrawable.getConstantState();

                if (drawableContainerState != null) {
                    Drawable[] children = drawableContainerState.getChildren();
                    if (children != null && children.length > 0) {

                        for (Drawable child : children) {
                            ////DevLogTool.getInstance(getContext()).saveLog("  child===>" + child);
                            if (child instanceof LayerDrawable) {
                                ////DevLogTool.getInstance(getContext()).saveLog("  GradientDrawable===>1");
                                LayerDrawable    selectedItem     = (LayerDrawable) child;
                                GradientDrawable selectedDrawable = (GradientDrawable) selectedItem.getDrawable(0);
                                selectedDrawable.setCornerRadius(mCornerRadius);
                                selectedDrawable.setAlpha(20);
                            } else if (child instanceof GradientDrawable) {
                                ////DevLogTool.getInstance(getContext()).saveLog("  GradientDrawable===>2");
                                GradientDrawable gradientDrawable = (GradientDrawable) child;
                                gradientDrawable.setCornerRadius(mCornerRadius);
                                gradientDrawable.setAlpha(20);
                            }
                        }

                        //                        LayerDrawable    selectedItem     = (LayerDrawable) children[0];
                        //                        GradientDrawable selectedDrawable = (GradientDrawable) selectedItem.getDrawable(0);
                        //                        selectedDrawable.setCornerRadius(mCornerRadius);
                        //                        selectedDrawable.setAlpha(200);
                        //
                        //                        if (children.length > 1) {
                        //                            LayerDrawable    unselectedItem     = (LayerDrawable) children[1];
                        //                            GradientDrawable unselectedDrawable = (GradientDrawable) unselectedItem.getDrawable(0);
                        //                            unselectedDrawable.setCornerRadius(mCornerRadius);
                        //                        }
                    }
                }

                //                StateListDrawable      gradientDrawable       = (StateListDrawable) inflatedView.getBackground();
                //                DrawableContainerState drawableContainerState = (DrawableContainerState) gradientDrawable.getConstantState();
                //                Drawable[]             children               = drawableContainerState.getChildren();
                //                LayerDrawable          selectedItem           = (LayerDrawable) children[0];
                //                LayerDrawable          unselectedItem         = (LayerDrawable) children[1];
                //                GradientDrawable       selectedDrawable       = (GradientDrawable) selectedItem.getDrawable(0);
                //                GradientDrawable       unselectedDrawable     = (GradientDrawable) unselectedItem.getDrawable(0);
                //                selectedDrawable.setStroke(STORKE_SIZE, NOTIFICATION_COLOR);
                //                unselectedDrawable.setStroke(STORKE_SIZE, NOTIFICATION_COLOR);
            } else if (mForegroundSelector instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) mForegroundSelector;
                gradientDrawable.setCornerRadius(mCornerRadius);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public JzbRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mForegroundSelector != null) {
            mForegroundSelector.setBounds(mShadowSize, mShadowSize, w - mShadowSize, h - mShadowSize);
        }
    }

    @Override
    protected boolean verifyDrawable(@NonNull Drawable who) {
        return super.verifyDrawable(who) || (who == mForegroundSelector);
    }

    @Override
    public void drawableHotspotChanged(float x, float y) {
        super.drawableHotspotChanged(x, y);
        if (mForegroundSelector != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mForegroundSelector.setHotspot(x, y);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //        if (isAutoClearFocus) {
        //            switch (event.getAction()) {
        //                case MotionEvent.ACTION_DOWN:
        //                    mTouchDownX = event.getX();
        //                    mTouchDownY = event.getY();
        //                    break;
        //                case MotionEvent.ACTION_MOVE:
        //                    float currentX = event.getX();
        //                    float currentY = event.getY();
        //
        //                    break;
        //                case MotionEvent.ACTION_UP:
        //                    mTouchDownX = 0;
        //                    mTouchDownY = 0;
        //                    break;
        //                case MotionEvent.ACTION_CANCEL:
        //                    mTouchDownX = 0;
        //                    mTouchDownY = 0;
        //                    break;
        //            }
        //            //            if (event.getAction() == MotionEvent.ACTION_DOWN) {
        //            //                View focusView = ((Activity) getContext()).getCurrentFocus();
        //            //                if (focusView instanceof EditText) {
        //            //                    LogTool.getInstance().saveLog(focusView.toString(), "           清除 焦点 =====>    000");
        //            //                    Rect outRect = new Rect();
        //            //                    focusView.getGlobalVisibleRect(outRect);
        //            //                    if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
        //            //                        LogTool.getInstance().saveLog(focusView.toString(), "           清除 焦点 =====>    111 ");
        //            //                        focusView.clearFocus();
        //            //                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //            //                        if (imm != null) {
        //            //                            LogTool.getInstance().saveLog(focusView.toString(), "           清除 焦点 =====>    222 ");
        //            //                            imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        //            //                        }
        //            //                        ((EditText) focusView).setCursorVisible(false);
        //            //                    }
        //            //                }
        //            //            }
        //        }

        if (mJzbViewFocusManager != null) {
            mJzbViewFocusManager.dispatchTouchEvent(event);
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //        Drawable background = getBackground();
        //        background.setBounds();
        ///////////////////////////////////////////////////////////////////////////
        // 阴影
        ///////////////////////////////////////////////////////////////////////////
        drawShadow(canvas);
        ///////////////////////////////////////////////////////////////////////////
        // 阴影
        ///////////////////////////////////////////////////////////////////////////

        if (mForegroundSelector != null) {
            mForegroundSelector.draw(canvas);
        }
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        if (mForegroundSelector != null) {
            ////DevLogTool.getInstance(getContext()).saveLog("  mForegroundSelector drawableStateChanged===>1");
            mForegroundSelector.setState(getDrawableState());
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                updateForeGroundDrawable();
            }
        }
    }

    @Override
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        if (mForegroundSelector != null) {
            mForegroundSelector.jumpToCurrentState();
        }
    }

    protected void drawShadow(Canvas canvas) {
        if (mShadowSize > 0) {
            int         paintColor  = mPaint.getColor();
            int         alpha       = mPaint.getAlpha();
            float       strokeWidth = mPaint.getStrokeWidth();
            Paint.Style paintStyle  = mPaint.getStyle();

            //---
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(mShadowColor);
            mPaint.setStrokeWidth(1);

            int alphaUnit = 255 / mShadowSize;

            int width  = getWidth();
            int height = getHeight();

            mShadowRectF.set(0, 0, width, height);


            for (int i = 0; i < mShadowSize; i++) {
                mPaint.setAlpha(alphaUnit * (i + 1));
                mShadowRectF.inset(1, 1);
                if (mCornerRadius > 0) {
                    canvas.drawRoundRect(mShadowRectF, mCornerRadius, mCornerRadius, mPaint);
                } else {
                    canvas.drawRect(mShadowRectF, mPaint);
                }
            }

            //---
            mPaint.setColor(paintColor);
            mPaint.setStrokeWidth(strokeWidth);
            mPaint.setStyle(paintStyle);
            mPaint.setAlpha(alpha);
        }
    }
}
