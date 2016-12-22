package chen.zheng.floatinglog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class FloatingLog {
    private static LogUpdateService.LocalBinder mBinder;
    public final static int LOG_NO_PRINT = -1;
    public final static int LOG_INVALID_PARAMETER = -2;
    public final static int LOG_NOT_PREPARED = -3;

    private static boolean mShowLine = true;
    private static boolean mShowScreen = true;

    public static void init(Context context, boolean showLine, boolean showScreen) {
        mShowLine = showLine;
        mShowScreen = showScreen;
        Intent bindIntent = new Intent(context, LogUpdateService.class);
        context.bindService(bindIntent, connection, Context.BIND_AUTO_CREATE);
        NotificationUtils.getInstance().init(context);
        if (mShowScreen) {
            NotificationUtils.getInstance().showButtonNotify();
        }
    }

    private static ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (LogUpdateService.LocalBinder) service;
        }
    };

    public static int v(String tag, String msg) {
        if (mBinder == null) return LOG_NOT_PREPARED;
        if (tag == null || msg == null) return LOG_INVALID_PARAMETER;
        if (mShowScreen) return mBinder.v(tag, callMethodAndLine() + msg);
        else return LOG_NO_PRINT;
    }

    public static int d(String tag, String msg) {
        if (mBinder == null) return LOG_NOT_PREPARED;
        if (tag == null || msg == null) return LOG_INVALID_PARAMETER;
        if (mShowScreen) return mBinder.d(tag, callMethodAndLine() + msg);
        else return LOG_NO_PRINT;
    }

    public static int i(String tag, String msg) {
        if (mBinder == null) return LOG_NOT_PREPARED;
        if (tag == null || msg == null) return LOG_INVALID_PARAMETER;
        if (mShowScreen) return mBinder.i(tag, callMethodAndLine() + msg);
        else return LOG_NO_PRINT;
    }

    public static int w(String tag, String msg) {
        if (mBinder == null) return LOG_NOT_PREPARED;
        if (tag == null || msg == null) return LOG_INVALID_PARAMETER;
        if (mShowScreen) return mBinder.w(tag, callMethodAndLine() + msg);
        else return LOG_NO_PRINT;
    }

    public static int e(String tag, String msg) {
        if (mBinder == null) return LOG_NOT_PREPARED;
        if (tag == null || msg == null) return LOG_INVALID_PARAMETER;
        if (mShowScreen) return mBinder.e(tag, callMethodAndLine() + msg);
        else return LOG_NO_PRINT;
    }

    private static String callMethodAndLine() {
        if (!mShowLine)
            return "";
        String result = "at ";
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[2];

        result += "." + thisMethodStack.getMethodName();
        result += "(" + thisMethodStack.getFileName();
        result += ":" + thisMethodStack.getLineNumber() + ")  ";

        return result;
    }
}
