package com.pengjunwei.android.custom.demo.miui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jzb.common.LogTool;

import hugo.weaving.DebugLog;

/**
 * Created by wikipeng on 2017/12/5.
 */
public abstract class BaseMIUIActivity extends AppCompatActivity {

    @DebugLog
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogTool.getInstance().saveLog(getClass().getName(),LogTool.getInstance().getCurrentMethodName());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogTool.getInstance().saveLog(getClass().getName(),LogTool.getInstance().getCurrentMethodName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogTool.getInstance().saveLog(getClass().getName(),LogTool.getInstance().getCurrentMethodName());
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogTool.getInstance().saveLog(getClass().getName(),LogTool.getInstance().getCurrentMethodName());
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogTool.getInstance().saveLog(getClass().getName(),LogTool.getInstance().getCurrentMethodName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogTool.getInstance().saveLog(getClass().getName(),LogTool.getInstance().getCurrentMethodName());
    }

    @DebugLog
    @Override
    protected void onResume() {
        super.onResume();
        LogTool.getInstance().saveLog(getClass().getName(),LogTool.getInstance().getCurrentMethodName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogTool.getInstance().saveLog(getClass().getName(),LogTool.getInstance().getCurrentMethodName());
    }
}
