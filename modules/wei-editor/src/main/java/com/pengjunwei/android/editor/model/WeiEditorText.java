package com.pengjunwei.android.editor.model;

/**
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorText {
    public String text;
    public String hint;
    public boolean isRequestFocus;
    /**是否之前有焦点*/
    public boolean isFocus;

    public void buildFirstHint(){
        hint = "我也来发表我的内容！";
    }
}
