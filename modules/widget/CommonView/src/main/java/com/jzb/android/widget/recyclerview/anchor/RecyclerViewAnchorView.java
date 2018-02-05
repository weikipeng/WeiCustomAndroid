package com.jzb.android.widget.recyclerview.anchor;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jzb.android.widget.showcase.Target;
import com.jzb.android.widget.showcase.ViewTarget;

/**
 * Created by wikipeng on 2017/6/16.
 */
public class RecyclerViewAnchorView {
    public  int    adapterPosition;
    public  View   view;
    /**
     * 锚点视图是否自动消失
     */
    public boolean                     isAutoDismiss;
    /**
     * 锚点视图显示布局参数
     */
    public RelativeLayout.LayoutParams layoutParams;
    /**
     * 锚点视图显示的区域限制
     */
    public View                        limitAreaView;
    /**
     * 是否可见
     */
    protected boolean               isVisible;

    // 锚点视图显示参数
    protected Target                limitAreaTarget;
    //---
    protected RecyclerViewDecorView decorView;
    private   View                  targetView;

    ///////////////////////////////////////////////////////////////////////////
    //
    ///////////////////////////////////////////////////////////////////////////
    private View    defaultTargetView;
    private Target  target;
    private Target  defaultTarget;
    /**
     * 是否已经显示了
     */
    private boolean isShow;


    public boolean isExistTarget() {
        return target != null || defaultTarget != null;
    }

    public Target getTarget() {
        if (target == null) {
            return defaultTarget;
        }

        return target;
    }

    public boolean isDefaultTarget() {
        return targetView == null && defaultTargetView != null;
    }

    public Target getLimitAreaTarget() {
        return limitAreaTarget;
    }

    public void setDecorView(RecyclerViewDecorView decorView) {
        this.decorView = decorView;
    }

    public void show() {
        if (!isVisible) {
            dismiss();
            return;
        }
        if (isShow) {
            return;
        }

        if (decorView != null) {
            ViewCompat.setAlpha(view, 1);
            decorView.addView(view);
            isShow = true;
            //处理自动消失
            handleAutoDismiss();
        }
    }

    public void dismiss() {
        if (isAutoDismiss) {
            isVisible = false;
        }
        isShow = false;
        if (decorView != null) {
            decorView.removeView(view);
        }
    }

    public void handleAutoDismiss() {
        if (isAutoDismiss && decorView != null) {
            decorView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    autoDismiss();
                }
            }, 1500);
        }
    }

    public void autoDismiss() {
        if (view == null) {
            return;
        }
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        alphaAnimator.setDuration(300);
        alphaAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dismiss();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        alphaAnimator.start();
    }

    public boolean isShow() {
        return isShow;
    }

    /**
     * 设置视图布局参数
     */
    protected void setViewParams(RelativeLayout.LayoutParams layoutParams) {
        this.layoutParams = layoutParams;
    }

    /**
     * 指定视图的显示区域限制
     */
    protected void setLimitArea(View view) {
        if (view == this.limitAreaView) {
            return;
        }
        this.limitAreaView = view;
        this.limitAreaTarget = view == null ? null : new ViewTarget(view);
    }

    public void setTargetView(View view) {
        if (this.targetView == view) {
            return;
        }
        this.targetView = view;
        this.target = view == null ? null : new ViewTarget(view);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void setAutoDismiss(boolean isAutoDismiss) {
        this.isAutoDismiss = isAutoDismiss;
    }

    public void setDefaultTargetView(View view, boolean isNeedUpdate) {
        if (this.defaultTargetView == view) {
            return;
        }
        //        /*FOpenLog.e("----- ======== jzbFocus debug updateDefaultTargetView: adapterPosition" + adapterPosition + " viewTarget:" + viewTarget);*/
        //        anchorDefaultTargetArray.put(adapterPosition, viewTarget);
        this.defaultTargetView = view;

        this.defaultTarget = view == null ? null : new ViewTarget(view);


        if (isNeedUpdate) {
            update();
        }
    }

    public void update() {
        if (decorView != null) {
            decorView.handleAnchorView(this);
        }
    }

    public static class Builder {
        private RecyclerViewAnchorView anchorView;

        public Builder(Activity activity, int adapterPosition, @LayoutRes int layoutId) {
            View view = LayoutInflater.from(activity).inflate(layoutId, (ViewGroup) activity.getWindow().getDecorView(), false);
            anchorView.view = view;
            anchorView.adapterPosition = adapterPosition;
            anchorView.setVisible(true);
        }

        public Builder(int adapterPosition, View view) {
            anchorView = new RecyclerViewAnchorView();
            anchorView.view = view;
            anchorView.adapterPosition = adapterPosition;
            anchorView.setVisible(true);
        }


        public Builder target(View view) {
            anchorView.setTargetView(view);
            return this;
        }

        public Builder layoutParams(RelativeLayout.LayoutParams layoutParams) {
            anchorView.setViewParams(layoutParams);
            return this;
        }

        public Builder limitArea(View limitView) {
            anchorView.setLimitArea(limitView);
            return this;
        }

        public Builder autoDismiss(boolean isAutoDismiss) {
            anchorView.setAutoDismiss(isAutoDismiss);
            return this;
        }

        public Builder visible(boolean isVisible) {
            anchorView.setVisible(isVisible);
            return this;
        }

        public RecyclerViewAnchorView build() {
            return anchorView;
        }

    }

}
