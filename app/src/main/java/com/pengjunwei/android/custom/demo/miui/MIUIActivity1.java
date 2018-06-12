package com.pengjunwei.android.custom.demo.miui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.jzb.common.LogTool;
import com.pengjunwei.android.custom.demo.R;
import com.pengjunwei.android.custom.demo.util.MemoryTool;

import java.util.ArrayList;
import java.util.List;

import static com.pengjunwei.android.custom.demo.memory.MemoryService.ACTION_EAT_MEMORY;

/**
 * Created by wikipeng on 2017/12/5.
 */
public class MIUIActivity1 extends BaseMIUIActivity {
    protected Button btnAction;
    protected Button btnAction100;
    protected Button btnAction50;
    protected Button btnAction20;
    private Context mContext;
    private static List<byte[]> testList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_miui_1);
        btnAction = findViewById(R.id.btnAction);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MIUIActivity1.this, MIUIActivity2.class);
                startActivity(intent);
            }
        });

        btnAction100 = findViewById(R.id.btnAction100);
        btnAction50 = findViewById(R.id.btnAction50);
        btnAction20 = findViewById(R.id.btnAction20);

        showMemInfo();

        btnAction100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                long memFreeSize = MemoryTool.getMemFreeSize();
//                LogTool.getInstance().saveLog("可用内存大小==>",memFreeSize);
////                NativeTool nativeTool = new NativeTool();
////                nativeTool.eatMemory(memFreeSize * 1024);
//                MemoryTool.eat100(mContext);
                byte[] bytes = new byte[100 * 1024 * 1024];
                testList.add(bytes);
                Intent intent = new Intent();
                String packageName = getPackageName();
                LogTool.getInstance().saveLog("packageName==>" + packageName);
                intent.setAction(packageName + "." + ACTION_EAT_MEMORY);
                startService(intent);
            }
        });

        btnAction50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                long memFreeSize = MemoryTool.getMemFreeSize();
//                LogTool.getInstance().saveLog("可用内存大小==>",memFreeSize);
//                MemoryTool.eat50(mContext);

                byte[] bytes = new byte[50 * 1024 * 1024];
                testList.add(bytes);

//                Intent intent = new Intent(mContext, MemoryFree100Service.class);
//                intent.setAction(ACTION_EAT_MEMORY);
//                startService(intent);
            }
        });

        btnAction20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                long memFreeSize = MemoryTool.getMemFreeSize();
//                LogTool.getInstance().saveLog("可用内存大小==>",memFreeSize);
//                MemoryTool.eat20(mContext);
                byte[] bytes = new byte[20 * 1024 * 1024];
                testList.add(bytes);

//                Intent intent = new Intent(mContext, MemoryFree100Service.class);
//                intent.setAction(ACTION_EAT_MEMORY);
//                startService(intent);
            }
        });
    }

    private void showMemInfo() {
        MemoryTool.displayBriefMemory(this);
        LogTool.getInstance().saveLog("内存大小==>", MemoryTool.getTotalRAM());
        LogTool.getInstance().saveLog("可用内存大小==>", MemoryTool.getMemFreeSize());

        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        if (activityManager != null) {
            activityManager.getMemoryInfo(memoryInfo);
        }

        long totalMem = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            totalMem = memoryInfo.totalMem;
        }
        long availMem = memoryInfo.availMem;

        LogTool.getInstance().saveLog("totalMem==>", totalMem, "  availMem==>", availMem);
        LogTool.getInstance().saveLog("totalMem==>", MemoryTool.formatMemSize(totalMem)
                , "  availMem==>", MemoryTool.formatMemSize(availMem));
    }


}
