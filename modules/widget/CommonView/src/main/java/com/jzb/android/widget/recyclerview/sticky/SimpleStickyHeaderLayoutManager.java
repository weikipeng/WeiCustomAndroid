package com.jzb.android.widget.recyclerview.sticky;

import android.app.Activity;
import android.content.Context;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.jzb.android.widget.recyclerview.anchor.RecyclerViewAnchorView;
import com.jzb.android.widget.recyclerview.anchor.RecyclerViewDecorView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wikipeng on 2017/6/12.
 */

public class SimpleStickyHeaderLayoutManager extends LinearLayoutManager {
    protected SparseArrayCompat<RecyclerView.ViewHolder> stickyViewHolderArray;
    protected RecyclerView.Adapter                       mAdapter;
    /**
     * 当前固定头部的数据索引位置
     */
    protected int                                        mStickyPosition;

    protected List<RecyclerViewDecorView> decorViewArray;

    protected int mLastFirstVisiblePosition = -100;
    protected int mLastFirstCompletelyVisibleItemPosition = -100;

    protected Context mContext;

    protected String logTag;

    protected RecyclerView mRecyclerView;
    protected Listener     mListener;
    private   long         lastRunTime;

    public SimpleStickyHeaderLayoutManager(Context context) {
        super(context);
        init(context);
    }

    protected void init(Context context) {
        mContext = context;

        stickyViewHolderArray = new SparseArrayCompat<>();
        decorViewArray = new ArrayList<>();
    }

    //    @Override
    //    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
    //        /*FOpenLog.e("jzbFocus debug SimpleStickyHeaderLayoutManager onMeasure =====> heightSpec:"+View.MeasureSpec.toString(heightSpec));*/
    //        super.onMeasure(recycler, state, widthSpec, heightSpec);
    //    }
    //
    //    @Override
    //    public void measureChild(View child, int widthUsed, int heightUsed) {
    //        /*FOpenLog.e("jzbFocus debug SimpleStickyHeaderLayoutManager measureChild =====> heightUsed:"+heightUsed);*/
    //        super.measureChild(child, widthUsed, heightUsed);
    //    }
    //
    //    @Override
    //    public void measureChildWithMargins(View child, int widthUsed, int heightUsed) {
    //        /*FOpenLog.e("jzbFocus debug SimpleStickyHeaderLayoutManager measureChildWithMargins =====> heightUsed:"+heightUsed);*/
    //        super.measureChildWithMargins(child, widthUsed, heightUsed);
    ////        final RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
    ////
    ////        final Rect insets = mRecyclerView.getItemDecorInsetsForChild(child);
    ////        widthUsed += insets.left + insets.right;
    ////        heightUsed += insets.top + insets.bottom;
    ////
    ////        final int widthSpec = getChildMeasureSpec(getWidth(), getWidthMode(),
    ////                getPaddingLeft() + getPaddingRight() +
    ////                        lp.leftMargin + lp.rightMargin + widthUsed, lp.width,
    ////                canScrollHorizontally());
    ////        final int heightSpec = getChildMeasureSpec(getHeight(), getHeightMode(),
    ////                getPaddingTop() + getPaddingBottom() +
    ////                        lp.topMargin + lp.bottomMargin + heightUsed, lp.height,
    ////                canScrollVertically());
    ////        if (shouldMeasureChild(child, widthSpec, heightSpec, lp)) {
    ////            child.measure(widthSpec, heightSpec);
    ////        }
    //    }

    public SimpleStickyHeaderLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
        init(context);
    }

    public SimpleStickyHeaderLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mAdapter = view.getAdapter();
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        super.onAdapterChanged(oldAdapter, newAdapter);
        mAdapter = newAdapter;
        stickyViewHolderArray.clear();
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        //        super.smoothScrollToPosition(recyclerView, state, position);
        LinearSmoothScroller linearSmoothScroller =
                new LinearSmoothScroller(recyclerView.getContext()) {
                    @Override
                    protected int calculateTimeForScrolling(int dx) {
                        // 此函数计算滚动dx的距离需要多久，当要滚动的距离很大时，比如说52000，
                        // 经测试，系统会多次调用此函数，每10000距离调一次，所以总的滚动时间
                        // 是多次调用此函数返回的时间的和，所以修改每次调用该函数时返回的时间的
                        // 大小就可以影响滚动需要的总时间，可以直接修改些函数的返回值，也可以修改
                        // dx的值，这里暂定使用后者.
                        // (See LinearSmoothScroller.TARGET_SEEK_SCROLL_DISTANCE_PX)
                        if (dx > 3000) {
                            dx = 3000;
                        }
                        return super.calculateTimeForScrolling(dx);
                    }
                };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);

    }

    //    public void updateStickyView(){
    //
    //    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //        if (!TextUtils.isEmpty(logTag)) {
        //            /*FOpenLog.e(logTag + "\n\n\njzbFocus debug onLayoutChildren =====");*/
        //        }
        //        updateStickyView(recycler, state);
        //        updateDecorView();
        //        notifyItemVisibleChange();
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        handleUIEvent();
    }

    protected void handleUIEvent() {
        //        if (System.currentTimeMillis() - lastRunTime < 300) {
        //            return;
        //        }
        //
        //        lastRunTime = System.currentTimeMillis();

        updateStickyView();
        updateDecorView();
        notifyItemVisibleChange();
    }

    /**
     * 更新固定头部的视图
     */
    protected void updateStickyView() {
        if (!(mAdapter instanceof IStickyHeaderDataProvider)) {
            return;
        }

        int itemCount = mAdapter.getItemCount();
        if (itemCount == 0) {
            return;
        }

        IStickyHeaderDataProvider dataProvider = (IStickyHeaderDataProvider) mAdapter;

        if (!dataProvider.isStickyEnable()) {
            return;
        }

        int firstVisibleItemPosition          = findFirstVisibleItemPosition();
        int lastCompletelyVisibleItemPosition = findLastCompletelyVisibleItemPosition();
        //当前需要固定头部的位置
        int stickyPosition = -1;
        //下一个需要固定头部的位置
        int nextStickyPosition = -1;

        for (int adapterPosition = firstVisibleItemPosition; adapterPosition >= 0; adapterPosition--) {
            if (dataProvider.isStickyTypeView(adapterPosition)) {
                stickyPosition = adapterPosition;
                break;
            }
        }

        //计算下一个需要固定头部的位置
        for (int i = firstVisibleItemPosition + 1; i <= lastCompletelyVisibleItemPosition; i++) {
            if (dataProvider.isStickyTypeView(i)) {
                nextStickyPosition = i;
                break;
            }
        }

        //        /*FOpenLog.e("jzbFocus debug ============ updateStickyView stickyPosition:" + stickyPosition
        //                + " nextStickyPosition:" + nextStickyPosition
        //                + " firstVisibleItemPosition:"+firstVisibleItemPosition
        //                + " mStickyPosition: "+mStickyPosition
        //        );*/


        if (mStickyPosition == stickyPosition) {
            checkNeedUpdateStickView(stickyPosition, nextStickyPosition);
            return;
        }

        mStickyPosition = stickyPosition;


        boolean isSticky     = false;
        boolean isNotify     = false;
        int     tempPosition = -1;

        int size = stickyViewHolderArray.size();

        //        String logMessage = "jzbFocus debug ============ dataProvider onShouldStickyView(======viewHolder:%s,======tempPosition:%s,======isSticky:%s)";

        RecyclerView.ViewHolder viewHolder = null;
        for (int i = 0; i < size; i++) {
            isSticky = false;
            tempPosition = stickyViewHolderArray.keyAt(i);
            if (tempPosition == stickyPosition) {
                isSticky = true;
                isNotify = true;
            }
            viewHolder = stickyViewHolderArray.get(tempPosition);

            if (viewHolder != null) {
                viewHolder.itemView.setVisibility(isSticky ? View.VISIBLE : View.GONE);
            }

            dataProvider.onShouldStickyView(viewHolder, tempPosition, isSticky);
            //            /*FOpenLog.e(String.format(Locale.getDefault(), logMessage, viewHolder, tempPosition, isSticky));*/
        }

        //如果已经通知过就返回
        if (isNotify) {
            checkNeedUpdateStickView(stickyPosition, nextStickyPosition);
            return;
        }

        //        /*FOpenLog.e("jzbFocus debug ============ createdViewHolder updateStickyView onShouldStickyView");*/
        RecyclerView.ViewHolder createdViewHolder = dataProvider.onShouldStickyView(null, stickyPosition, true);
        stickyViewHolderArray.put(stickyPosition, createdViewHolder);
    }

    /***更新相对固定的视图*/
    protected void updateDecorView() {
        if (mAdapter == null) {
            return;
        }
        int itemCount = mAdapter.getItemCount();
        if (itemCount == 0) {
            return;
        }

        int size = decorViewArray.size();
        if (size <= 0) {
            return;
        }

        ///////////////////////////////////////////////////////////////////////////
        // 挨个处理相对锚点
        ///////////////////////////////////////////////////////////////////////////
        //        RecyclerViewDecorView anchorView   = null;
        //        int                   tempPosition = -1;
        //        for (int i = 0; i < size; i++) {
        //            tempPosition = anchorViewArray.keyAt(i);
        //            anchorView = anchorViewArray.get(tempPosition);
        //            handleAnchorView(anchorView);
        //        }


        int firstVisibleItemPosition          = findFirstCompletelyVisibleItemPosition();
        int lastCompletelyVisibleItemPosition = findLastCompletelyVisibleItemPosition();

        int                          adapterPosition = -1;
        List<RecyclerViewAnchorView> anchorViewList  = null;

        Activity activity = (Activity) mContext;

        for (RecyclerViewDecorView decorView : decorViewArray) {
            if (decorView == null) {
                continue;
            }
            anchorViewList = decorView.getAnchorViewList();

            if (anchorViewList == null || anchorViewList.size() == 0) {
                continue;
            }

            for (RecyclerViewAnchorView anchorView : anchorViewList) {
                adapterPosition = anchorView.adapterPosition;
                if (adapterPosition < firstVisibleItemPosition || adapterPosition > lastCompletelyVisibleItemPosition) {
                    //                    anchorView.targetView = null;
                    anchorView.setTargetView(null);
                } else {
                    anchorView.setTargetView(findViewByPosition(adapterPosition));
                    //                    anchorView.targetView = findViewByPosition(adapterPosition);
                }
            }

            //            handleDecorView(decorView);
            if (decorView.isShow()) {
                decorView.update();
            } else {
                decorView.show(activity);
            }
        }
    }

    protected void notifyItemVisibleChange() {
        //        //DevLogTool.getInstance(mRecyclerView.getContext()).saveLog("notifyItemVisibleChange  ------ call");

        int childCount = getChildCount();

        RecyclerView.ViewHolder viewHolder = null;
        View                    view       = null;

        if (mListener != null && childCount > 0 && mRecyclerView != null) {
            int firstVisible                       = -1;
            int lastVisible                        = -1;
            int firstCompletelyVisibleItemPosition = -1;
            int lastCompletelyVisibleItemPosition  = -1;

            firstVisible = findFirstVisibleItemPosition();
            lastVisible = findLastVisibleItemPosition();
            firstCompletelyVisibleItemPosition = findFirstCompletelyVisibleItemPosition();
            lastCompletelyVisibleItemPosition = findLastCompletelyVisibleItemPosition();

            //            if (mLastFirstVisiblePosition == firstVisible
            //                    && mLastFirstCompletelyVisibleItemPosition == firstCompletelyVisibleItemPosition) {
            //                return;
            //            }

            mLastFirstVisiblePosition = firstVisible;
            mLastFirstCompletelyVisibleItemPosition = firstCompletelyVisibleItemPosition;

            //            //DevLogTool.getInstance(mRecyclerView.getContext()).saveLog("notifyItemVisibleChange  ------"
            //                    + " firstVisible" + firstVisible
            //                    + " lastVisible" + lastVisible
            //            );

            int position[] = new int[6];
            position[0] = firstVisible - 2;
            position[1] = firstVisible - 1;
            position[2] = firstVisible;
            position[3] = lastVisible;
            position[4] = lastVisible + 1;
            position[5] = lastVisible + 2;
            for (int i = 0; i < 6; i++) {
                //                view = getChildAt(i);
                if (position[i] < 0 || position[i] >= childCount) {
                    continue;
                }

                viewHolder = mRecyclerView.findViewHolderForAdapterPosition(position[i]);
                if (viewHolder instanceof Listener) {
                    ((Listener) viewHolder).onVisibleItemChange(firstVisible, lastVisible, firstCompletelyVisibleItemPosition, lastCompletelyVisibleItemPosition);
                }
            }

            //            for (int i = 0; i < childCount; i++) {
            //                view = getChildAt(i);
            //                viewHolder = mRecyclerView.getChildViewHolder(view);
            //                if (viewHolder instanceof Listener) {
            //                    ((Listener)viewHolder).onVisibleItemChange(firstVisible, lastVisible, firstCompletelyVisibleItemPosition, lastCompletelyVisibleItemPosition);
            //                }
            //            }
            mListener.onVisibleItemChange(firstVisible, lastVisible, firstCompletelyVisibleItemPosition, lastCompletelyVisibleItemPosition);
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 相对固定的视图
    ///////////////////////////////////////////////////////////////////////////

    protected void checkNeedUpdateStickView(int stickyPosition, int nextStickyPosition) {
        if (stickyPosition < 0 || nextStickyPosition <= stickyPosition) {
            return;
        }

        RecyclerView.ViewHolder viewHolder = null;
        viewHolder = stickyViewHolderArray.get(stickyPosition);
        if (viewHolder != null) {
            updateStickyViewPosition(viewHolder.itemView, stickyPosition, nextStickyPosition);
        }
    }

    /**
     * 滑动时更新头部固定视图的位置
     */
    protected void updateStickyViewPosition(View stickyView, int stickyPosition, int nextStickyPosition) {
        if (stickyPosition < 0 || nextStickyPosition < 0 || (stickyPosition >= nextStickyPosition) || stickyView == null) {
            return;
        }
        if (nextStickyPosition > stickyPosition) {
            View nextStickyView = findViewByPosition(nextStickyPosition);
            if (nextStickyView == null) {
                return;
            }

            //----
            int nextStickyViewTop = getDecoratedTop(nextStickyView);

            int height = stickyView.getHeight();

            if (nextStickyViewTop < height) {
                //                /*FOpenLog.e("jzbFocus debug updateStickyViewPosition======= < height nextStickyViewTop:" + nextStickyViewTop + " height:" + height
                //                        + " stickyPosition:" + stickyPosition + " nextStickyPosition:" + nextStickyPosition);*/

                stickyView.setTranslationY(nextStickyViewTop - height);
            } else {
                //                /*FOpenLog.e("jzbFocus debug updateStickyViewPosition======= nextStickyViewTop:" + nextStickyViewTop + " height:" + height
                //                        + " stickyPosition:" + stickyPosition + " nextStickyPosition:" + nextStickyPosition);*/
            }
        }
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int scrollVerticallyBy = super.scrollVerticallyBy(dy, recycler, state);
        //        if (!TextUtils.isEmpty(logTag)) {
        //            /*FOpenLog.e(logTag + "\n\n\njzbFocus debug onLayoutChildren =====scrollVerticallyBy:" + scrollVerticallyBy);*/
        //        }
        handleUIEvent();
        return scrollVerticallyBy;
    }

    //
    public void showDecorView(RecyclerViewDecorView decorView) {
        if (!decorViewArray.contains(decorView)) {
            decorViewArray.add(decorView);
        }

        updateDecorView();
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void setLogTag(String logTag) {
        this.logTag = logTag;
    }

    public void setJzbRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
    }

    public interface Listener {
        boolean onVisibleItemChange(int firstVisible, int lastVisible, int firstCompletelyVisibleItemPosition, int lastCompletelyVisibleItemPosition);
    }
}
