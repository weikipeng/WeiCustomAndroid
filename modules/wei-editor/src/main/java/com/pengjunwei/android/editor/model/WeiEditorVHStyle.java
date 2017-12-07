package com.pengjunwei.android.editor.model;

/**
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorVHStyle {
    private int flag;
    /**
     * 改变了布局参数
     */
    public static final int FLAG_LAYOUT_PARAMS = 1;

    private int width;
    private int height;

    public void setLayoutParams(int width, int height) {
        flag = flag | FLAG_LAYOUT_PARAMS;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isNeedUpdate(int flag) {
        return (this.flag & flag) > 0;
    }
}
