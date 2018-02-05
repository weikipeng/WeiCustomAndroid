package com.jzb.android.widget.decorView;

import android.app.Activity;

/**
 * Created by wikipeng on 2017/6/16.
 */

public interface IDecorView {
    void dismiss(boolean isRemove);
    /**
     * 临时隐藏，与其他窗体冲突时调用
     */
    void tempDismiss();
    void show(final Activity activity);
}
