package com.pengjunwei.common.lib;

public class LogTool {

    public static boolean FLAG = true;
    private static LogTool  sDevLogTool;
    private        Delegate mDelegate;

    private LogTool() {

    }

    public static LogTool getInstance() {
        if (sDevLogTool == null) {
            sDevLogTool = new LogTool();
        }
        return sDevLogTool;
    }


    public void setDelegate(Delegate delegate) {
        mDelegate = delegate;
    }

    public synchronized void saveLog(String log) {
        if (!FLAG) {
            return;
        }

        StackTraceElement[] stackTrace    = Thread.currentThread().getStackTrace();
        String              fullClassName = stackTrace[3].getClassName();
        String              className     = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        int                 lineNumber    = stackTrace[3].getLineNumber();

        String codeMessage = "";
        if (stackTrace.length > 5) {
            String supClassName = stackTrace[4].getClassName();
            supClassName = supClassName.substring(supClassName.lastIndexOf(".") + 1);

            int    supLinenumber = stackTrace[4].getLineNumber();
            String methodName    = stackTrace[4].getMethodName();

            codeMessage += supClassName + "." + methodName + ":" + supLinenumber + "==>";
        }

        codeMessage += "(" + className + ":" + lineNumber + "@" + Thread.currentThread().getName() + ") : ";

        //        Log.e("jzb", "jzbFocus debug " + codeMessage + log);

        if (mDelegate != null) {
            mDelegate.executeLog(className, "jzbFocus debug " + codeMessage + log);
        }
    }

    public synchronized void saveLog(String log, Object... messages) {
        if (!FLAG) {
            return;
        }

        StackTraceElement[] stackTrace    = Thread.currentThread().getStackTrace();
        String              fullClassName = stackTrace[3].getClassName();
        String              className     = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        int                 lineNumber    = stackTrace[3].getLineNumber();

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

        if (mDelegate != null) {
            mDelegate.executeLog("jzb", "jzbFocus debug " + codeMessage + log);
        }
        //        Log.e("jzb", "jzbFocus debug " + codeMessage + log);
    }

    public String getCurrentMethodName() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 3) {
            return stackTrace[3].getMethodName();
        }

        return "";
    }

    public interface Delegate {
        void executeLog(String tag, String message);
    }

}

