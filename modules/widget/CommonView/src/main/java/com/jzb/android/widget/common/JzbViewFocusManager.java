package com.jzb.android.widget.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jzb.common.LogTool;

/**
 * Created by wikipeng on 2018/1/6.
 */
public class JzbViewFocusManager {
    @NonNull
    protected Context mContext;

    ///////////////////////////////////////////////////////////////////////////
    // 触摸事件
    ///////////////////////////////////////////////////////////////////////////
    protected float   mTouchDownX;
    protected float   mTouchDownY;

    protected boolean isAutoClearFocus;

    protected MotionEvent mCurrentMotionEvent;

    public JzbViewFocusManager(@NonNull Context context, AttributeSet attrs) {
        this.mContext = context;
        TypedArray layoutTypedArray = context.obtainStyledAttributes(attrs, R.styleable.JzbLayout);
        isAutoClearFocus = layoutTypedArray.getBoolean(R.styleable.JzbLayout_isAutoClearFocus, false);

        layoutTypedArray.recycle();
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        if (isAutoClearFocus) {
            mCurrentMotionEvent = event;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mTouchDownX = event.getX();
                    mTouchDownY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float currentX = event.getX();
                    float currentY = event.getY();
                    if (Math.abs(currentX - mTouchDownX) > 0
                            || Math.abs(currentY - mTouchDownY) > 0) {
                        clearCurrentFocus();
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    mTouchDownX = 0;
                    mTouchDownY = 0;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    mTouchDownX = 0;
                    mTouchDownY = 0;
                    break;
            }
        }

        return false;
    }

    public boolean clearCurrentFocus() {
        //        View focusView = ((Activity) mContext).getCurrentFocus();
        //        if (focusView instanceof EditText) {
        //            LogTool.getInstance().s(focusView.toString(), "           清除 焦点 =====>    000");
        //            Rect outRect = new Rect();
        //            focusView.getGlobalVisibleRect(outRect);
        //            if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
        //                LogTool.getInstance().s(focusView.toString(), "           清除 焦点 =====>    111 ");
        //                focusView.clearFocus();
        //                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        //                if (imm != null) {
        //                    LogTool.getInstance().s(focusView.toString(), "           清除 焦点 =====>    222 ");
        //                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        //                }
        //                ((EditText) focusView).setCursorVisible(false);
        //            }
        //        }
        //
        //        return true;
        return clearCurrentFocus(mCurrentMotionEvent);
    }

    public boolean clearCurrentFocus(MotionEvent event) {
        if (event == null) {
            return false;
        }
        View focusView = ((Activity) mContext).getCurrentFocus();
        if (focusView instanceof EditText) {
//            LogTool.getInstance().s(focusView.toString(), "           清除 焦点 =====>    000");
            Rect outRect = new Rect();
            focusView.getGlobalVisibleRect(outRect);
            if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
//                LogTool.getInstance().s(focusView.toString(), "           清除 焦点 =====>    111 ");
                focusView.clearFocus();
                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
//                    LogTool.getInstance().s(focusView.toString(), "           清除 焦点 =====>    222 ");
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                }
                ((EditText) focusView).setCursorVisible(false);
            }
        }

        return true;
    }

}
