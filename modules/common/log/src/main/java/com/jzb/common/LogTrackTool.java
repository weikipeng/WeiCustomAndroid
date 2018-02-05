package com.jzb.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wikipeng on 2017/9/22.
 */
public class LogTrackTool {
    public static final String PATTERN_DETAIL = "yyyy-MM-dd HH:mm:ss";
    private static LogTrackTool sDevLogTool;
    private        File         sLogFile;
    private boolean isPrintPath = true;
    private boolean  isPrintLog;
    private Delegate mDelegate;

    private String lastClassName;

    public static LogTrackTool getInstance() {
        if (sDevLogTool == null) {
            synchronized (LogTrackTool.class) {
                if (sDevLogTool == null) {
                    sDevLogTool = new LogTrackTool();
                }
            }
        }

        return sDevLogTool;
    }

    public static LogTrackTool newInstance() {
        LogTrackTool result = null;
        synchronized (LogTrackTool.class) {
            result = new LogTrackTool();
        }
        return result;
    }

    public File getDefaultLogFile() {
        if (mDelegate != null) {
            return mDelegate.getDefaultLogFile();
        }
        return null;
    }

    public synchronized void clearLog() {
        if (mDelegate == null) {
            return;
        }
        File defaultLogFile = mDelegate.getDefaultLogFile();
        if (defaultLogFile != null) {
            defaultLogFile.delete();
        }
    }

    /**
     * 跟踪用户操作
     */
    public synchronized void t(String log, Object... messages) {
        if (sLogFile == null && mDelegate != null) {
            sLogFile = mDelegate.getDefaultLogFile();
        }

        if (sLogFile == null) {
            return;
        }

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        writeToFile(sLogFile, stackTrace, log, messages);
    }

    /**
     * 跟踪用户操作
     */
    private synchronized void writeToFile(File logFile, StackTraceElement[] stackTrace, String log, Object... messages) {
        if (stackTrace == null) {
            return;
        }

        String fullClassName = stackTrace[3].getClassName();
        String className     = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        int    lineNumber    = stackTrace[3].getLineNumber();

        String codeMessage = "";
        if (stackTrace.length > 5) {
            String supClassName = stackTrace[4].getClassName();
            supClassName = supClassName.substring(supClassName.lastIndexOf(".") + 1);

            int    supLinenumber = stackTrace[4].getLineNumber();
            String methodName    = stackTrace[4].getMethodName();

            codeMessage += supClassName + "." + methodName + ":" + supLinenumber + "==>";
        }

        codeMessage += "(" + className + ":" + lineNumber + "@" + Thread.currentThread().getName() + ") : ";

        if (messages != null && messages.length > 0) {
            StringBuilder logBuilder = new StringBuilder(log);
            for (Object message : messages) {
                logBuilder.append(message);
            }
            log = logBuilder.toString();
        }

        if (isPrintLog && mDelegate != null) {
            String tag = "jzbdev";

            StringBuilder printLogStringBuilder = new StringBuilder();
            printLogStringBuilder.append("jzbFocus debug ");
            if (isValidString(className)) {
                printLogStringBuilder.append(className);
            }
            printLogStringBuilder.append("[");
            printLogStringBuilder.append(codeMessage);
            printLogStringBuilder.append("]");

            String printLogText = String.format("%-222s %s", log, printLogStringBuilder);

            mDelegate.executeLog(tag, printLogText);
        }

        ///////////////////////////////////////////////////////////////////////////
        //
        ///////////////////////////////////////////////////////////////////////////
        if (mDelegate == null) {
            return;
        }

        if (logFile == null) {
            logFile = mDelegate.getDefaultLogFile();
        }

        if (logFile == null) {
            return;
        }

        try {
            if (!logFile.exists() || logFile.isDirectory()) {
                logFile.getParentFile().mkdirs();
                logFile.createNewFile();
            }

            FileWriter     fileWriter     = new FileWriter(logFile, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String         timeStampText  = getDetailTimeText(System.currentTimeMillis());
            bufferedWriter.append("\n======\n");
            bufferedWriter.append(timeStampText);
            bufferedWriter.append(":");
            bufferedWriter.append(codeMessage);
            bufferedWriter.append("\n======\n");
            bufferedWriter.append(log);
            if (lastClassName != null && !lastClassName.equals(className)) {
                bufferedWriter.newLine();
                bufferedWriter.newLine();
            }
            lastClassName = className;

            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected boolean isValidString(String stringText) {
        return stringText != null && !"".equals(stringText);
    }

    public static String getDetailTimeText(long time) {
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_DETAIL, Locale.getDefault());
        return format.format(new Date(time));
    }

    /**
     * 跟踪用户操作
     */
    public synchronized void s(Object classObject, Object... messages) {
        if (sLogFile == null && mDelegate != null) {
            sLogFile = mDelegate.getDefaultLogFile();
        }

        if (sLogFile == null) {
            return;
        }

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        String log = null;
        if (classObject != null) {
            log = String.format("%-64s %s", classObject.getClass().getSimpleName(), "==>");
        }

        writeToFile(sLogFile, stackTrace, log, messages);
    }

    /**
     * 跟踪用户操作
     */
    public synchronized void f(File tempLogFile, String log, Object... messages) {
        if (tempLogFile == null) {
            return;
        }

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        writeToFile(tempLogFile, stackTrace, log, messages);
    }

    public LogTrackTool setDebugMode(boolean isDebugMode) {
        isPrintLog = isDebugMode;
        return this;
    }

    public LogTrackTool setLogFile(File tempLogFile) {
        this.sLogFile = tempLogFile;
        return this;
    }

    public LogTrackTool setLogFile(String tempFileName) {
        if (mDelegate != null) {
            this.sLogFile = mDelegate.generateLogFile(tempFileName);
        }

        return this;
    }

    public LogTrackTool setDelegate(Delegate delegate) {
        this.mDelegate = delegate;
        return this;
    }

    public void build() {

    }

    public void resetDefaultLogFile() {
        if (mDelegate != null) {
            this.sLogFile = mDelegate.getDefaultLogFile();
        }
    }

    public interface Delegate {
        void executeLog(String tag, String message);

        File getDefaultLogFile();

        File generateLogFile(String fileName);
    }
}
