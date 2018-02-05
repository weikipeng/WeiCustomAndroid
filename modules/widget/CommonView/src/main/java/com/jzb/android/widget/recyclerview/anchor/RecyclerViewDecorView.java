package com.jzb.android.widget.recyclerview.anchor;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.jzb.android.widget.decorView.DecorViewManger;
import com.jzb.android.widget.decorView.IDecorView;
import com.jzb.android.widget.showcase.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wikipeng on 2017/6/18.
 */
public class RecyclerViewDecorView extends FrameLayout implements IDecorView {
    /**
     * Start intro once view is ready to show
     */
    protected boolean isReady;
    /**
     * Delay the intro view
     */
    protected Handler handler;

    protected List<RecyclerViewAnchorView> anchorViewList;

    protected int mWidthMeasureSpec;
    protected int mHeightMeasureSpec;

    protected boolean isShow;

    public RecyclerViewDecorView(@NonNull Context context) {
        super(context);
        init(context);
    }

    protected void init(Context context) {
        setWillNotDraw(false);
        setVisibility(INVISIBLE);

        isReady = false;
        handler = new Handler();
        anchorViewList = new ArrayList<>();
    }

    public RecyclerViewDecorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RecyclerViewDecorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RecyclerViewDecorView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mHeightMeasureSpec = heightMeasureSpec;
        this.mWidthMeasureSpec = widthMeasureSpec;
    }

    /**
     * Dissmiss view with reverse animation
     */
    public void dismiss() {
        dismiss(true);
    }

    @Override
    public void dismiss(boolean isRemove) {
        isShow = false;
        setVisibility(GONE);
        removeAnchorView();
        anchorViewList.clear();

        if (isRemove) {
            DecorViewManger.getInstance().remove(this);
        }
    }

    @Override
    public void tempDismiss() {
        dismiss(false);
    }

    /**
     * Show the view based on the configuration
     * Reveal is available only for Lollipop and above in other only fadein will work
     * To support reveal in older versions use github.com/ozodrukh/CircularReveal
     *
     * @param activity
     */
    public void show(final Activity activity) {
        DecorViewManger.getInstance().add(this);
        if (isShow) {
            return;
        }
        isShow = true;
        ((ViewGroup) activity.getWindow().getDecorView()).addView(this);
        setReady(true);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                doAnchorView();
            }
        }, 100);
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    protected void doAnchorView() {
        setVisibility(View.VISIBLE);

        for (RecyclerViewAnchorView anchorView : anchorViewList) {
            handleAnchorView(anchorView);
        }
    }

    public void handleAnchorView(RecyclerViewAnchorView anchorView) {
        if (anchorView == null) {
            return;
        }

        View view = anchorView.view;

        anchorView.setDecorView(this);

        if (view == null) {
            return;
        }

        if (!anchorView.isExistTarget()) {
//            /*FOpenLog.e("jzbFocus debug handleAnchorView viewTarget == null -----remove view isDefault:" + anchorView.isDefaultTarget());*/
            anchorView.dismiss();
            return;
        }
        Rect rect = anchorView.getTarget().getRect();
        int  top  = rect.bottom;
        int  left = rect.left;

        RelativeLayout.LayoutParams layoutParams = anchorView.layoutParams;

        ///////////////////////////////////////////////////////////////////////////
        // 测绘
        ///////////////////////////////////////////////////////////////////////////
        int measuredWidth  = view.getWidth();
        int measuredHeight = view.getHeight();
        if (measuredWidth == 0) {
            measureChildWithMargins(view, mWidthMeasureSpec, 0, mHeightMeasureSpec, 0);
            measuredWidth = view.getMeasuredWidth();
            measuredHeight = view.getMeasuredHeight();
        }


        //布局是否右对齐
        boolean isAlignRight = false;
        if (layoutParams != null) {
            int[] rules = layoutParams.getRules();
            if (rules[RelativeLayout.ALIGN_RIGHT] != 0) {
                isAlignRight = true;
            }

            //根据左右对齐来计算距离
            if (isAlignRight) {
                left = rect.right - measuredWidth - layoutParams.rightMargin;
            } else {
                left = left + layoutParams.leftMargin;
            }
        }


        ViewCompat.setTranslationX(view, left);
        ViewCompat.setTranslationY(view, top);

        ///////////////////////////////////////////////////////////////////////////
        // 判断显示是否超出了限制区域
        ///////////////////////////////////////////////////////////////////////////
        Target limitTarget = anchorView.getLimitAreaTarget();

        if (limitTarget != null) {
            Rect   limitRect    = limitTarget.getRect();
//            String limitRectLog = "jzbFocus debug handleAnchorView -----limitRect[%d,%d,%d,%d]";
//            /*FOpenLog.e(String.format(Locale.getDefault(), limitRectLog, limitRect.left, limitRect.top, limitRect.right, limitRect.bottom));*/

            Rect viewRect = new Rect(left, top, left + measuredWidth, top + measuredHeight);
            if (!limitRect.contains(viewRect)) {
                anchorView.dismiss();
                return;
            }
        }

        ///////////////////////////////////////////////////////////////////////////
        //
        ///////////////////////////////////////////////////////////////////////////
        if (view.getParent() == null) {
//            /*FOpenLog.e("jzbFocus debug handleAnchorView -----top:" + top + "  left:" + left + " isDefault:" + anchorView.isExistTarget());*/
            anchorView.show();
        } else {
//            /*FOpenLog.e("jzbFocus debug handleAnchorView 已经addView-----top:" + top + "  left:" + left + " isDefault:" + anchorView.isExistTarget());*/
        }
    }

    /**
     * Remove the anchor view
     */
    private void removeAnchorView() {
        if (getParent() != null)
            ((ViewGroup) getParent()).removeView(this);
    }

    public void updateOrAddAnchorView(RecyclerViewAnchorView anchorView) {
        if (!anchorViewList.contains(anchorView)) {
            anchorViewList.add(anchorView);
        }
    }

    public void update() {
        if (isShow) {
            doAnchorView();
        }
    }

    public boolean isShow() {
        return isShow;
    }

    public void clearViewTarget() {
        //        anchorTargetArray.clear();
        anchorViewList.clear();
    }

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    public List<RecyclerViewAnchorView> getAnchorViewList() {
        return anchorViewList;
    }

    public static class Builder {
        private Activity              activity;
        private RecyclerViewDecorView decorView;

        public Builder(Activity activity) {
            this.activity = activity;
            decorView = new RecyclerViewDecorView(activity);
        }

        public RecyclerViewDecorView show() {
            build().show(activity);
            return decorView;
        }

        public RecyclerViewDecorView build() {
            return decorView;
        }
    }

}

