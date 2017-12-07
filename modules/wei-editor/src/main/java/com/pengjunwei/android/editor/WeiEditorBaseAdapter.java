package com.pengjunwei.android.editor;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.pengjunwei.android.editor.viewholder.WeiEditorVHDelegate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorBaseAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    public List<T> mDataList;

    protected WeiEditorVHDelegate delegate;

    public WeiEditorBaseAdapter(WeiEditorVHDelegate delegate) {
        super();
        mDataList = new ArrayList<>();
        this.delegate = delegate;
    }

    @Override
    public V onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(V holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void add(T data) {
        mDataList.add(data);
    }

}
