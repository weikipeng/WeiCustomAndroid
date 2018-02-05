package com.jzb.android.widget.recyclerview.sticky;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

/**
 * Created by wikipeng on 2017/6/12.
 */

public interface IStickyHeaderDataProvider {
    /**
     * 是否当前position是固定头部类型
     */
    boolean isStickyTypeView(int position);

    /**
     * 是否支持固定头部
     */
    boolean isStickyEnable();

    /**
     * @param viewHolder 需要固定的视图 第一次为null,以后返回为上次的视图
     * @return 返回Viewholder
     */
    RecyclerView.ViewHolder onShouldStickyView(@Nullable RecyclerView.ViewHolder viewHolder, int stickyAdapterPosition, boolean isSticky);

    ViewDelegate getStickyViewDelegate();

    void setStickyViewDelegate(ViewDelegate delegate);

    interface ViewDelegate {
        boolean onStickyView(@Nullable RecyclerView.ViewHolder viewHolder, int stickyAdapterPosition, boolean isSticky);
        void showStickyView(boolean isShow);
        void updateStickyView();
    }
}
