package com.jzb.android.widget.recyclerview.sticky;

/**
 * RecyclerView 的 data 更新 是否 固定在头部状态
 * Created by wikipeng on 2017/6/15.
 */
public interface IRecyclerStickyStore {
    /**
     * 返回是否固定在头部
     */
    boolean isSticky();

    /**
     * 设置当前固定在头部状态
     */
    void setSticky(boolean isSticky);
}
