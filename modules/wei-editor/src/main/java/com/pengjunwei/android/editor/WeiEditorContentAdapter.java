package com.pengjunwei.android.editor;

import com.pengjunwei.android.editor.viewholder.WeiEditorVHBase;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHDelegate;

/**
 * Created by wikipeng on 2017/12/8.
 */
public class WeiEditorContentAdapter<D> extends WeiEditorTemplateAdapter<D>{

    public WeiEditorContentAdapter(WeiEditorVHDelegate delegate) {
        super(delegate);
    }

    @Override
    public void onViewAttachedToWindow(WeiEditorVHBase holder) {
//        LogTool.getInstance().saveLog(holder.getClass().getName(),"    onViewAttachedToWindow======> "+holder);
        super.onViewAttachedToWindow(holder);
//        LogTool.getInstance().saveLog(holder.getClass().getName(),"    onViewAttachedToWindow======> "+holder);
    }

    @Override
    public void onViewDetachedFromWindow(WeiEditorVHBase holder) {
//        LogTool.getInstance().saveLog(holder.getClass().getName(),"    ______onViewDetachedFromWindow======> "+holder);
        super.onViewDetachedFromWindow(holder);
//        LogTool.getInstance().saveLog(holder.getClass().getName(),"    ______onViewDetachedFromWindow======> "+holder);
    }
}
