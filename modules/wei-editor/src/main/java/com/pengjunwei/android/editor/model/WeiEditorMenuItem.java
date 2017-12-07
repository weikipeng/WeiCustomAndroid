package com.pengjunwei.android.editor.model;

import android.support.annotation.DrawableRes;

/**
 * 编辑器菜单子项
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorMenuItem {
    public String  key;
    @DrawableRes
    public int     iconRes;
    public boolean isSelected;
//    public int[]   marginArray;

    public WeiEditorMenuItem(String key, int iconRes) {
        this.key = key;
        this.iconRes = iconRes;
//        marginArray = new int[]{0, 0, 0, 0};
    }
}
