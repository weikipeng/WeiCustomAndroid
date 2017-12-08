package com.pengjunwei.android.editor;

import android.view.ViewGroup;

import com.pengjunwei.android.editor.model.WeiEditorVHBaseData;
import com.pengjunwei.android.editor.model.WeiEditorVHStyle;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHBase;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHDelegate;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHProvider;

/**
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorTemplateAdapter<D> extends WeiEditorBaseAdapter<WeiEditorVHBaseData<D>, WeiEditorVHBase> {
    protected WeiEditorVHProvider customProvider;

    public WeiEditorTemplateAdapter(WeiEditorVHDelegate delegate) {
        super(delegate);

    }

    @Override
    public WeiEditorVHBase onCreateViewHolder(ViewGroup parent, int viewType) {
        WeiEditorVHBase viewHolder = null;
        if (customProvider != null) {
            viewHolder = customProvider.onCreateViewHolder(parent, viewType, delegate);
        }

        if (viewHolder == null) {
            viewHolder = WeiEditorVHProvider.getGlobalProvider().onCreateViewHolder(parent, viewType, delegate);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(WeiEditorVHBase holder, int position) {
        if (position >= 0 && position < mDataList.size()) {
            holder.onBindViewHolder(position, mDataList.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= 0 && position < mDataList.size()) {
            WeiEditorVHBaseData<D> tempData = mDataList.get(position);
            return tempData.type;
        }

        return super.getItemViewType(position);
    }

    @Override
    public void onViewAttachedToWindow(WeiEditorVHBase holder) {
        super.onViewAttachedToWindow(holder);
        if (holder != null) {
            holder.onViewAttachedToWindow();
        }

    }

    @Override
    public void onViewDetachedFromWindow(WeiEditorVHBase holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder != null) {
            holder.onViewDetachedFromWindow();
        }
    }

    public void addData(Object data) {
        //        int viewType = 0;
        //        if (customProvider != null) {
        //            viewType = customProvider.getViewType(data.getClass());
        //        }
        //
        //        if (viewType == 0) {
        //            viewType = WeiEditorVHProvider.getGlobalProvider().getViewType(data.getClass());
        //        }
        //
        //        add(new WeiEditorVHBaseData(viewType, data));
        addData(data, null);
    }

    public void addData(Object data, WeiEditorVHStyle style) {
        int viewType = 0;
        if (customProvider != null) {
            viewType = customProvider.getViewType(data.getClass());
        }

        if (viewType == 0) {
            viewType = WeiEditorVHProvider.getGlobalProvider().getViewType(data.getClass());
        }
        WeiEditorVHBaseData baseData = new WeiEditorVHBaseData(viewType, data);
        baseData.setStyle(style);
        add(baseData);
    }

    public void setCustomProvider(WeiEditorVHProvider customProvider) {
        this.customProvider = customProvider;
    }

    public WeiEditorVHBaseData create(Object data, WeiEditorVHStyle style) {
        int viewType = 0;
        if (customProvider != null) {
            viewType = customProvider.getViewType(data.getClass());
        }

        if (viewType == 0) {
            viewType = WeiEditorVHProvider.getGlobalProvider().getViewType(data.getClass());
        }
        WeiEditorVHBaseData baseData = new WeiEditorVHBaseData(viewType, data);
        baseData.setStyle(style);
        return baseData;
    }
}
