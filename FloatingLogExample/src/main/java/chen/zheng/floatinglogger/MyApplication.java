package chen.zheng.floatinglogger;

import android.app.Application;

import chen.zheng.floatinglog.FloatingLog;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FloatingLog.init(this,true,true);
    }
}
