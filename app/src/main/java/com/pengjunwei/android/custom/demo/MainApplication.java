package com.pengjunwei.android.custom.demo;

import android.app.Application;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.jzb.common.LogTool;


/**
 * Created by wikipeng on 2017/12/5.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        LogTool.getInstance().setDelegate(new LogTool.Delegate() {
            @Override
            public void executeLog(String tag, String message) {
                Log.e(tag, message);
            }
        });
    }
}
