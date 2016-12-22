package chen.zheng.floatinglogger;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Random;

import chen.zheng.floatinglog.FloatingLog;


/**
 * 目的：创建一个浮动在系统窗口的半透明界面，显示当前应用的Log信息
 */
public class MainActivity extends Activity {
    private static final int HANDLER_MSG_V = 1;
    private static final int HANDLER_MSG_D = 2;
    private static final int HANDLER_MSG_I = 3;
    private static final int HANDLER_MSG_W = 4;
    private static final int HANDLER_MSG_E = 5;

    private static final int MAX_HANDLER_MSG_ID = HANDLER_MSG_E;
    private static final int MAX_LOG_DELAY = 1000;


    private static final String TAG = "Main";
    private Handler mHandler = new MyHandler();
    private Random mRandom;
    private int mBackGroundCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRandom = new Random();
        mHandler.sendEmptyMessageDelayed(HANDLER_MSG_V, MAX_LOG_DELAY);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    mBackGroundCount++;
                    if (mBackGroundCount % 5 == HANDLER_MSG_V) {
                        FloatingLog.v(TAG, "Background V log");
                    } else if (mBackGroundCount % 5 == HANDLER_MSG_D) {
                        FloatingLog.d(TAG, "Background D log");
                    } else if (mBackGroundCount % 5 == HANDLER_MSG_I) {
                        FloatingLog.i(TAG, "Background I log");
                    } else if (mBackGroundCount % 5 == HANDLER_MSG_W) {
                        FloatingLog.w(TAG, "Background W log");
                    } else if (mBackGroundCount % 5 == HANDLER_MSG_E) {
                        FloatingLog.e(TAG, "Background E log ");
                    }
                    try {
                        Thread.sleep(MAX_LOG_DELAY);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_MSG_V:
                    FloatingLog.v(TAG, "V log:I amm long log , how long ? thissssssssss log~~~~");
                    mHandler.sendEmptyMessageDelayed(getRadomID(), getRadomTime());
                    break;
                case HANDLER_MSG_D:
                    FloatingLog.d(TAG, "D log:I amm long log , how long ? thissssssssss log~~~~");
                    mHandler.sendEmptyMessageDelayed(getRadomID(), getRadomTime());
                    break;
                case HANDLER_MSG_I:
                    FloatingLog.i(TAG, "I log:I amm long log , how long ? thissssssssss log~~~~");
                    mHandler.sendEmptyMessageDelayed(getRadomID(), getRadomTime());
                    break;
                case HANDLER_MSG_W:
                    FloatingLog.w(TAG, "W log:I amm long log , how long ? thissssssssss log~~~~");
                    mHandler.sendEmptyMessageDelayed(getRadomID(), getRadomTime());
                    break;
                case HANDLER_MSG_E:
                    FloatingLog.e(TAG, "E log:I amm long log , how long ? thissssssssss log~~~~");
                    mHandler.sendEmptyMessageDelayed(getRadomID(), getRadomTime());
                    break;
            }
        }

        private int getRadomID() {
            return mRandom.nextInt(MAX_HANDLER_MSG_ID) + 1;
        }

        private int getRadomTime() {
            return mRandom.nextInt(MAX_LOG_DELAY) + 1;
        }
    }

}
