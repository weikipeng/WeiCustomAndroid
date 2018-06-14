package com.pengjunwei.android.custom.demo.util;

import android.app.ActivityManager;
import android.content.Context;

import com.jzb.common.LogTool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.Context.ACTIVITY_SERVICE;

public class MemoryTool {

    public static String getTotalRAM() {

        RandomAccessFile reader = null;
        String load = null;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        double totRam = 0;
        String lastValue = "";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();

            // Get the Number value from the string
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
                // System.out.println("Ram : " + value);
            }
            reader.close();

            totRam = Double.parseDouble(value);
            // totRam = totRam / 1024;

            double mb = totRam / 1024.0;
            double gb = totRam / 1048576.0;
            double tb = totRam / 1073741824.0;

            if (tb > 1) {
                lastValue = twoDecimalForm.format(tb).concat(" TB");
            } else if (gb > 1) {
                lastValue = twoDecimalForm.format(gb).concat(" GB");
            } else if (mb > 1) {
                lastValue = twoDecimalForm.format(mb).concat(" MB");
            } else {
                lastValue = twoDecimalForm.format(totRam).concat(" KB");
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Streams.close(reader);
        }

        return lastValue;
    }

    public long getMemTotalSize() {
        RandomAccessFile reader = null;
        String load = null;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        long totalRam = 0;

        String lastValue = "";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();

            // Get the Number value from the string
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
                // System.out.println("Ram : " + value);
            }
            reader.close();

            totalRam = Long.parseLong(value);

            totalRam = totalRam * 1024;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Streams.close(reader);
        }

        return totalRam;
    }

    public static long getMemFreeSize() {
        RandomAccessFile reader = null;
        String load = null;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        long totalRam = 0;

        String lastValue = "";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();
            LogTool.getInstance().s("reader===>", load);

            load = reader.readLine();
            LogTool.getInstance().s("reader 222 ===>", load);


            // Get the Number value from the string
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
                // System.out.println("Ram : " + value);
            }
            reader.close();

            totalRam = Long.parseLong(value);

            totalRam = totalRam * 1024;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Streams.close(reader);
        }

        return totalRam;
    }

    public static void eat100(Context context) {
        readAssetsUnsafe(context, "LargeTestFile.img");
    }

    public static void eat50(Context context) {
        readAssetsUnsafe(context, "LargeTestFile50.img");
    }

    public static void eat20(Context context) {
        readAssetsUnsafe(context, "LargeTestFile20.img");
    }

    /**
     * 从assets中获取文件并读取数据（资源文件只能读不能写）
     */
    public static String readAssetsUnsafe(Context context, String fileName) {
        String res = null;
        InputStream is = null;
        try {
            is = context.getAssets().open(fileName);
            res = readInputStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//                is = null;
//            }
//        }
        return res;
    }

    /**
     * 从一个InputStrem中读取String内容
     *
     * @throws IOException
     */
    private static String readInputStream(InputStream is) throws IOException {
//        int length = is.available();
//        byte[] buffer = new byte[length];
//        is.read(buffer);
//        return new String(buffer, "UTF-8");
        ///
//        char cbuf[] = new char[1024 * 8];
//        InputStreamReader inputStreamReader = new InputStreamReader(is);
//        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//        bufferedReader.read(cbuf, 0, )
        return "";
    }

    public static String formatMemSize(long size) {
        String[] unit = {"B", "KB", "MB", "GB"};
        int n = 0;
        long tempSize = size;
        while ((tempSize = tempSize / 1024) != 0) {
            n++;
        }

        double tempY = Math.pow(1024, n);
        double result = size / tempY;
        BigDecimal bigDecimal = new BigDecimal(result);
        BigDecimal realResult = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_EVEN);
        return realResult.toString() + (n < unit.length && n >= 0 ? unit[n] : "单位错误");
    }

    public static void displayBriefMemory(Context context) {
        final ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        LogTool.getInstance().s("系统剩余内存:" + (info.availMem >> 10) + "k");
        LogTool.getInstance().s("系统是否处于低内存运行：" + info.lowMemory);
        LogTool.getInstance().s("当系统剩余内存低于" + info.threshold + "时就看成低内存运行");
    }
}
