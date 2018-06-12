package com.pengjunwei.android.custom.demo.util;

public class NativeTool {

    static {
        System.loadLibrary("native-lib");
    }

    public native long eatMemory(long size);
}
