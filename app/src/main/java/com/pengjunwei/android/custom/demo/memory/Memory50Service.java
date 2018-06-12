package com.pengjunwei.android.custom.demo.memory;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class Memory50Service extends MemoryService {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void eatMemory() {
        eatMemory(50 * 1024 * 1024);
    }
}