package com.pengjunwei.android.custom.demo.recyclerview.manager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.pengjunwei.common.lib.LogTool;

/**
 * Created by wikipeng on 2017/12/15.
 */
public class WeiLinearLayoutManager extends LinearLayoutManager {
    protected RecyclerView mWeiRecyclerView;

    public WeiLinearLayoutManager(Context context) {
        super(context);
    }

    public WeiLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public WeiLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        super.onMeasure(recycler, state, widthSpec, heightSpec);
        //        LogTool.getInstance().saveLog("\n\n\n     WeiLinearLayoutManager onMeasure===>", ""
        //                , "\nrecycler===>", recycler
        //                , "\nstate===>", state
        //        );
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //        LogTool.getInstance().saveLog("WeiLinearLayoutManager onLayoutChildren===>");
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        LogTool.getInstance().saveLog("WeiLinearLayoutManager onLayoutCompleted===>");
        int itemCount = getItemCount();
        if (itemCount > 0) {
            View lastView = findViewByPosition(itemCount - 1);

            StringBuilder stringBuilder = new StringBuilder("         measureChildWithMargins===> ");
            stringBuilder.append("\nlastView===>").append(lastView);
            stringBuilder.append("\nitemCount===>").append(itemCount);

            //---
            int lastViewTop             = lastView.getTop();
            int lastViewDecoratedTop    = getTopDecorationHeight(lastView);
            int lastViewDecoratedBottom = getBottomDecorationHeight(lastView);

            if (mWeiRecyclerView != null) {
                int recyclerViewBottom = mWeiRecyclerView.getBottom();
                stringBuilder.append("\nrecyclerViewBottom===>").append(recyclerViewBottom);
                stringBuilder.append("\nlastViewBottom     222===>").append(lastViewTop);
                stringBuilder.append("\nlastViewDecoratedTop===>").append(lastViewDecoratedTop);
                stringBuilder.append("\nlastViewDecoratedBottom===>").append(lastViewDecoratedBottom);

                ViewGroup.LayoutParams layoutParams = lastView.getLayoutParams();

                layoutParams.height = recyclerViewBottom - lastViewTop;

                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;

                    stringBuilder.append("\nmarginLayoutParams.topMargin===>").append(marginLayoutParams.topMargin);
                    stringBuilder.append("\nmarginLayoutParams.bottomMargin===>").append(marginLayoutParams.bottomMargin);

                    layoutParams.height = layoutParams.height - marginLayoutParams.bottomMargin;


                    //                    layoutParams.height = 758;
                    //                    layoutParams.height = 773;

                    stringBuilder.append("\nheight===>").append(layoutParams.height);
                }
                //                lastView.setLayoutParams(layoutParams);
                //                lastView.forceLayout();
                //                lastView.requestLayout();
            }

            LogTool.getInstance().saveLog(stringBuilder.toString());
        }
    }

    @Override
    public void measureChildWithMargins(View child, int widthUsed, int heightUsed) {
        //        int position = getPosition(child);
        //
        //        int itemCount = getItemCount();
        //        LogTool.getInstance().saveLog("         measureChildWithMargins===> ",""
        //        ,"\nchild===>",child
        //        ,"\nwidthUsed===>",widthUsed
        //        ,"\nheightUsed===>",heightUsed
        //        ,"\nposition===>",position
        //        ,"\nitemCount===>",itemCount
        //        );
        super.measureChildWithMargins(child, widthUsed, heightUsed);
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically();
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return super.scrollVerticallyBy(dy, recycler, state);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) {
        super.onItemsAdded(recyclerView, positionStart, itemCount);
    }

    @Override
    public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) {
        super.onItemsRemoved(recyclerView, positionStart, itemCount);
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);
    }

    public void setRecyclerViewW(RecyclerView recyclerView) {
        this.mWeiRecyclerView = recyclerView;
    }
}
