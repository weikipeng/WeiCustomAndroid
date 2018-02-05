package com.jzb.android.widget.showcase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.AttrRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jzb.android.widget.decorView.DecorViewManger;
import com.jzb.android.widget.decorView.IDecorView;
import com.jzb.android.widget.showcase.shape.ShowcaseShapeRectangle;
import com.jzb.common.LogTrackTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.jzb.android.widget.showcase.ShowcaseView.Builder.KEY_PRE;

/**
 * Created by wikipeng on 2017/6/15.
 */
public class ShowcaseView extends FrameLayout implements IDecorView {
    protected boolean                                dismissOnBackPress;
    protected Map<Target, View>                      showcaseList;
    protected Map<View, RelativeLayout.LayoutParams> showcaseLayoutParamsMap;
    /**
     * Overlay rectangle above the view
     */
    protected List<ShowcaseShapeRectangle>           rectangleShapeList;
    /**
     * Start intro once view is ready to show
     */
    protected boolean                                isReady;
    protected int                                    mWidthMeasureSpec;
    protected int                                    mHeightMeasureSpec;
    //---
    protected SharedPreferences                      mSharedPreferences;
    protected String                                 mPreferenceKey;
    protected boolean                                isShowOnce;
    /**
     * 是否消失后一起关闭同一页面
     */
    protected boolean                                isDismissCloseAll;
    /**
     * OverLay color
     */
    //    private int maskColor = 0x70000000;
    private int maskColor = Color.TRANSPARENT;
    /**
     * Dismiss layout on touch
     */
    private boolean dismissOnTouch;
    private boolean enableDismissAfterShown;
    /**
     * Eraser to erase the circle area
     */
    private Paint   eraser;
    private Bitmap  bitmap;
    private Canvas  canvas;
    /**
     * Padding for circle or rectangle
     */
    //    private int padding = 20;
    private int padding = 0;
    /**
     * View Width
     */
    private int               width;
    /**
     * View Height
     */
    private int               height;
    /**
     * Delay the intro view
     */
    private Handler           handler;
    ///////////////////////////////////////////////////////////////////////////
    // 6.8.2
    ///////////////////////////////////////////////////////////////////////////
    private OnDismissListener mDismissListener;

    public ShowcaseView(@NonNull Context context) {
        super(context);
        init(context);
    }

    protected void init(Context context) {
        setWillNotDraw(false);
        setVisibility(INVISIBLE);

        showcaseList = new HashMap<>();
        showcaseLayoutParamsMap = new HashMap<>();
        rectangleShapeList = new ArrayList<>();
        isReady = false;
        handler = new Handler();
        dismissOnBackPress = false;
        enableDismissAfterShown = false;
        dismissOnBackPress = false;

        eraser = new Paint();
        eraser.setColor(0xFFFFFFFF);
        eraser.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraser.setFlags(Paint.ANTI_ALIAS_FLAG);
    }

    public ShowcaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowcaseView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ShowcaseView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 是否已经显示过一次
     */
    public static boolean isShowOnceAlready(Context context, String key) {
        key = KEY_PRE + key;
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return defaultSharedPreferences.getBoolean(key, false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mHeightMeasureSpec = heightMeasureSpec;
        this.mWidthMeasureSpec = widthMeasureSpec;

        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ///////////////////////////////////////////////////////////////////////////
                //
                ///////////////////////////////////////////////////////////////////////////
                //
                //                if (isTouchOnFocus && isPerformClick) {
                //                    targetView.getView().setPressed(true);
                //                    targetView.getView().invalidate();
                //                }

                return true;
            case MotionEvent.ACTION_UP:
                if (dismissOnTouch) {
                    dismiss();
                }

                ///////////////////////////////////////////////////////////////////////////
                //
                ///////////////////////////////////////////////////////////////////////////
                //                if (isTouchOnFocus || dismissOnTouch)
                //                    dismiss();
                //
                //                if (isTouchOnFocus && isPerformClick) {
                //                    targetView.getView().performClick();
                //                    targetView.getView().setPressed(true);
                //                    targetView.getView().invalidate();
                //                    targetView.getView().setPressed(false);
                //                    targetView.getView().invalidate();
                //                }

                return true;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas layoutCanvas) {
        super.onDraw(layoutCanvas);
        try {
            if (!isReady)
                return;

            if (bitmap == null || layoutCanvas == null) {
                if (bitmap != null) {
                    bitmap.recycle();
                }

                if (width == 0 || height == 0) {
                    return;
                }

                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                this.canvas = new Canvas(bitmap);
            }

            this.canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            this.canvas.drawColor(maskColor);

            for (ShowcaseShapeRectangle rectangle : rectangleShapeList) {
                if (rectangle == null) {
                    continue;
                }

                rectangle.draw(this.canvas, eraser, padding);
            }
            if (layoutCanvas != null) {
                layoutCanvas.drawBitmap(bitmap, 0, 0, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Dissmiss view with reverse animation
     */
    public void dismiss() {
        dismiss(true);
    }

    @Override
    public void dismiss(boolean isRemove) {
        if (isRemove) {
            DecorViewManger.getInstance().remove(this);
        }

        //        /*FOpenLog.e("jzbFocus debug showCaseView dismiss ======>");*/
        if (isShowOnce && !TextUtils.isEmpty(mPreferenceKey)) {
            if (mSharedPreferences == null) {
                mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            }

            mSharedPreferences.edit().putBoolean(mPreferenceKey, true).apply();
        }
        setVisibility(GONE);
        removeShowcaseView();
        showcaseList.clear();
        showcaseLayoutParamsMap.clear();
        rectangleShapeList.clear();

        if (isDismissCloseAll) {
            DecorViewManger.getInstance().dismissAll();
        }

        if (mDismissListener != null) {
            mDismissListener.onDismiss(this);
        }

        ///////////////////////////////////////////////////////////////////////////
        //
        ///////////////////////////////////////////////////////////////////////////
        //        preferencesManager.setDisplayed(usageId);
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        //            if (isRevealAnimationEnabled)
        //                exitRevealAnimation();
        //            else
        //                startFadeout();
        //        } else {
        //            startFadeout();
        //        }
    }

    /**
     * 临时隐藏，与其他窗体冲突时调用
     */
    public void tempDismiss() {
        //        /*FOpenLog.e("jzbFocus debug showCaseView ====== tempDismiss ======>");*/
        setVisibility(GONE);
        removeShowcaseView();
    }

    /**
     * Show the view based on the configuration
     * Reveal is available only for Lollipop and above in other only fadein will work
     * To support reveal in older versions use github.com/ozodrukh/CircularReveal
     *
     * @param activity
     */
    public void show(final Activity activity) {
        //        /*FOpenLog.e("jzbFocus debug showCaseView ====== show show ======>");*/
        DecorViewManger.getInstance().add(this);
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        if (getParent() == null) {
            decorView.addView(this);
        }

        setVisibility(VISIBLE);
        requestFocus();

        setReady(true);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doShowcaseView();
            }
        }, 100);
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    protected void doShowcaseView() {
        setVisibility(View.VISIBLE);

        Set<Map.Entry<Target, View>>      entries  = showcaseList.entrySet();
        Iterator<Map.Entry<Target, View>> iterator = entries.iterator();

        Map.Entry<Target, View> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            handleShowcaseView(entry.getKey(), entry.getValue());
        }
    }

    protected void handleShowcaseView(Target viewTarget, View view) {
        Rect                        rect         = viewTarget.getRect();
        int                         top          = rect.top + viewTarget.getViewHeight() / 2;
        int                         left         = rect.left + viewTarget.getViewWidth() / 2;
        RelativeLayout.LayoutParams layoutParams = showcaseLayoutParamsMap.get(view);

        //布局是否右对齐
        if (layoutParams != null) {
            int[] rules = layoutParams.getRules();

            int measuredWidth  = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            if (measuredWidth == 0) {
                ViewGroup.LayoutParams tempLayoutParams = view.getLayoutParams();
                ///////////////////////////////////////////////////////////////////////////
                // 检测视图LayoutParams是否为空
                ///////////////////////////////////////////////////////////////////////////
                if (tempLayoutParams == null) {
                    int wrapContent = ViewGroup.LayoutParams.WRAP_CONTENT;
                    tempLayoutParams = new ViewGroup.LayoutParams(wrapContent, wrapContent);
                    view.setLayoutParams(tempLayoutParams);
                }

                if (tempLayoutParams instanceof MarginLayoutParams) {
                    measureChildWithMargins(view, mWidthMeasureSpec, 0, mHeightMeasureSpec, 0);
                } else {
                    measureChild(view, mWidthMeasureSpec, mHeightMeasureSpec);
                }
                measuredWidth = view.getMeasuredWidth();
                measuredHeight = view.getMeasuredHeight();
            }


            //根据左右对齐来计算距离
            if (rules[RelativeLayout.ALIGN_RIGHT] != 0) {
                left = rect.right - measuredWidth;
                /*FOpenLog.e("jzbFocus debug handleShowcaseView alignRight-----measuredWidth:" + measuredWidth + "  left:" + left);*/
            } else if (rules[RelativeLayout.ALIGN_LEFT] != 0) {
                left = rect.left;
            }

            //根据左右对齐来计算距离
            left = left + layoutParams.leftMargin - layoutParams.rightMargin;


            if (rules[RelativeLayout.BELOW] != 0) {
                top = rect.bottom;
            } else if (rules[RelativeLayout.ABOVE] != 0) {
                top = rect.top - measuredHeight;
            } else if (rules[RelativeLayout.ALIGN_BOTTOM] != 0) {
                //底部对齐
                top = rect.bottom - measuredHeight;
            }

            top = top + layoutParams.topMargin - layoutParams.bottomMargin;
        }


        view.setTranslationX(left);
        view.setTranslationY(top);
        addLayoutView(view);
    }

    protected void addLayoutView(View view) {
        if (view == null) {
            return;
        }

        ViewParent parent = view.getParent();
        if (parent == this) {
            view.setVisibility(VISIBLE);
            return;
        }

        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }

        addView(view);
    }

    /**
     * Remove the spotlight view
     */
    private void removeShowcaseView() {
        if (getParent() != null) {
            ((ViewGroup) getParent()).removeView(this);
        }

        ///////////////////////////////////////////////////////////////////////////
        //
        ///////////////////////////////////////////////////////////////////////////
        //        if (listener != null)
        //            listener.onUserClicked(usageId);
        //
        //        if (getParent() != null)
        //            ((ViewGroup) getParent()).removeView(this);
    }

    protected void setDismissCloseAll(boolean isCloseAll) {
        this.isDismissCloseAll = isCloseAll;
    }

    protected void addViewParams(View showView, RelativeLayout.LayoutParams layoutParams) {
        showcaseLayoutParamsMap.put(showView, layoutParams);
    }

    public void addTargetView(Target targetView, View showView) {
        showcaseList.put(targetView, showView);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (dismissOnBackPress) {
            if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                dismiss();
                return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }

    private void setMaskColor(int maskColor) {
        this.maskColor = maskColor;
    }

    public void setDismissOnBackPress(boolean dismissOnBackPress) {
        this.dismissOnBackPress = dismissOnBackPress;
    }

    public void setDismissOnTouch(boolean dismissOnTouch) {
        this.dismissOnTouch = dismissOnTouch;
    }

    private void enableHandleKeyEvent() {
        setFocusableInTouchMode(true);
        setFocusable(true);
        requestFocus();
    }

    public boolean isEnableDismissAfterShown() {
        return enableDismissAfterShown;
    }

    public void setEnableDismissAfterShown(boolean enableDismissAfterShown) {
        this.enableDismissAfterShown = enableDismissAfterShown;
    }

    public void addRectangleShape(ShowcaseShapeRectangle rectangle) {
        rectangleShapeList.add(rectangle);
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void setPreferenceKey(String preferenceKey, boolean isShowOnce) {
        this.isShowOnce = isShowOnce;
        this.mPreferenceKey = preferenceKey;
    }

    public void setOnDismissListener(OnDismissListener dismissListener) {
        mDismissListener = dismissListener;
    }

    public interface OnDismissListener {
        void onDismiss(Object... payloads);
    }

    public static class Builder {
        public static final String KEY_PRE = "showcase_";
        private Activity     activity;
        private ShowcaseView showcaseView;
        //        private boolean isRoundCornerHalf;

        public Builder(Activity activity) {
            this.activity = activity;
            showcaseView = new ShowcaseView(activity);
            // TODO: 2017/6/16
            //            showcaseView.setSoftwareBtnHeight(getSoftButtonsBarHeight(activity));
        }

        public ShowcaseView showOnce(String key) {
            key = KEY_PRE + key;
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
            if (defaultSharedPreferences.getBoolean(key, false)) {
                return showcaseView;
            }
            showcaseView.setPreferenceKey(key, true);
            show();
            return showcaseView;
        }

        public ShowcaseView show() {
            build().show(activity);
            return showcaseView;
        }

        public ShowcaseView build() {
            showcaseView.enableHandleKeyEvent();
            return showcaseView;
        }

        public Builder target(View targetView, @LayoutRes int layoutId, RelativeLayout.LayoutParams layoutParams) {
            View showView = LayoutInflater.from(activity).inflate(layoutId, showcaseView, false);
            return target(targetView, showView, layoutParams);
        }

        public Builder target(View targetView, View showView, RelativeLayout.LayoutParams layoutParams) {
            return target(targetView, showView, layoutParams, false);
        }

        public Builder target(View targetView, View showView, RelativeLayout.LayoutParams layoutParams, boolean isRoundCornerHalf) {
            //            ViewTarget viewTarget = new ViewTarget(targetView);
            //            showcaseView.addTargetView(viewTarget, showView);
            //            showcaseView.addViewParams(showView, layoutParams);
            //            ShowcaseShapeRectangle rectangle = new ShowcaseShapeRectangle(viewTarget, showcaseView.padding);
            //            //注意没起作用
            //            rectangle.setRoundCornerHalf(isRoundCornerHalf);
            //            showcaseView.addRectangleShape(rectangle);
            //            return this;

            return target(targetView, showView, layoutParams, true, isRoundCornerHalf);
        }

        public Builder target(View targetView, View showView, RelativeLayout.LayoutParams layoutParams, boolean isClipRect, boolean isRoundCornerHalf) {
            ViewTarget viewTarget = new ViewTarget(targetView);
            showcaseView.addTargetView(viewTarget, showView);
            showcaseView.addViewParams(showView, layoutParams);
            if (isClipRect) {
                ShowcaseShapeRectangle rectangle = new ShowcaseShapeRectangle(viewTarget, showcaseView.padding);
                //注意没起作用
                rectangle.setRoundCornerHalf(isRoundCornerHalf);
                showcaseView.addRectangleShape(rectangle);
            }
            return this;
        }


        public Builder maskColor(int maskColor) {
            showcaseView.setMaskColor(maskColor);
            return this;
        }

        public Builder dismissOnTouch(boolean dismissOnTouch) {
            showcaseView.setDismissOnTouch(dismissOnTouch);
            return this;
        }

        public Builder dismissOnBackPress(boolean dismissOnBackPress) {
            showcaseView.setDismissOnBackPress(dismissOnBackPress);
            return this;
        }

        public Builder dismissCloseAll(boolean isCloseAll) {
            showcaseView.setDismissCloseAll(isCloseAll);
            return this;
        }

        public Builder enableDismissAfterShown(boolean enable) {
            if (enable) {
                showcaseView.setEnableDismissAfterShown(enable);
                showcaseView.setDismissOnTouch(false);
            }
            return this;
        }

        public Builder targetPadding(int padding) {
            // TODO: 2017/6/16 wikipeng
            //            spotlightView.setPadding(padding);
            return this;
        }

        //        /**圆角矩形*/
        //        public Builder roundCornerHalf(boolean isRoundCornerHalf) {
        //            this.isRoundCornerHalf = isRoundCornerHalf;
        //            return this;
        //        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogTrackTool.getInstance().t("是否处理 返回键===>", dismissOnBackPress);
        if (!dismissOnBackPress) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //    public void setCircleShape(Circle circleShape) {
    //        this.circleShape = circleShape;
    //    }
}
