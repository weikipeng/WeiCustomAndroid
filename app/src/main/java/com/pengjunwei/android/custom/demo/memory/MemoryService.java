package com.pengjunwei.android.custom.demo.memory;

import android.app.Service;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.jzb.common.LogTool;
import com.pengjunwei.android.custom.demo.util.MemoryTool;

import java.util.ArrayList;
import java.util.List;

public abstract class MemoryService extends Service {
    private static List<byte[]> memoryList = new ArrayList<>();
    public static final String ACTION_EAT_MEMORY = "eat_memory";

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getPackageName());
        startForeground((int) System.currentTimeMillis(), builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogTool.getInstance().saveLog(getClass().getSimpleName(),"onStartCommand ===========");
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_EAT_MEMORY.equals(action)) {
                eatMemory();
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public abstract void eatMemory();

    protected void eatMemory(int memorySize) {
        byte[] bytes = new byte[memorySize];
        memoryList.add(bytes);
        LogTool.getInstance().saveLog("eat memory===>", MemoryTool.formatMemSize(memorySize));
    }
}
