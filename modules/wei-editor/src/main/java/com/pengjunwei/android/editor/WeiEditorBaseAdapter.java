package com.pengjunwei.android.editor;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorBaseAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<T> mDataList;

    public WeiEditorBaseAdapter() {
        super();
        mDataList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void add(T data) {
        mDataList.add(data);
    }
}
