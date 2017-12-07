package com.pengjunwei.android.editor.model;

import android.util.SparseArray;

/**
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorVHBaseData<T> {
    public int                           type;
    public T                             data;
    public SparseArray<WeiEditorVHStyle> styleArray;
    //    @LayoutRes
    //    public int layoutResId;

    //    public WeiEditorVHBaseData(T data, @LayoutRes int layoutResId) {
    //        this.type = layoutResId;
    //        this.data = data;
    ////        this.layoutResId = layoutResId;
    //    }

    public WeiEditorVHBaseData(int type, T data) {
        this.type = type;
        this.data = data;
    }

    public void setStyle(WeiEditorVHStyle style){
        if (styleArray == null) {
            styleArray = new SparseArray<>();
        }

        styleArray.put(0, style);
    }

    public WeiEditorVHStyle getStyle() {
        if (styleArray != null) {
            return styleArray.get(0);
        }
        return null;
    }
}
