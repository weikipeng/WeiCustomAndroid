package com.pengjunwei.android.editor.view;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;

import com.pengjunwei.common.lib.LogTool;

/**
 * Created by wikipeng on 2017/12/8.
 */
public class WeiEditorEditText extends TextInputEditText {
    public WeiEditorEditText(Context context) {
        super(context);
    }

    public WeiEditorEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeiEditorEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        LogTool.getInstance().saveLog("onKeyUp===>", keyCode
                , " event===>", event.toString()
        );
        return super.onKeyUp(keyCode, event);
    }

//    @Override
//    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
//        return super.onCreateInputConnection(outAttrs);
//    }
//
//    private class CustomInputConnection extends InputConnectionWrapper {
//
//        public CustomInputConnection(InputConnection target, boolean mutable) {
//            super(target, mutable);
//        }
//    }
}
