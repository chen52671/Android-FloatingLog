/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package chen.zheng.floatinglog;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class LogUpdateService extends Service {
    private static final String TAG = "LogUpdateService";
    public ViewGroup fView;
    private FloatLogView sFloatView;
    private IBinder mBinder = new LocalBinder();
    private SpannableBuilderWrapper mSpannableBuilderWrapper;
    private BroadcastReceiver mReceiver;

    public static boolean flterV = true;
    public static boolean flterD = true;
    public static boolean flterI = true;
    public static boolean flterW = true;

    public static final int MSG_V = 1;
    public static final int MSG_D = 2;
    public static final int MSG_I = 3;
    public static final int MSG_W = 4;
    public static final int MSG_E = 5;

    private Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mSpannableBuilderWrapper = new SpannableBuilderWrapper();
        initButtonReceiver();
        mHandler = new UIHandler();/*防止后台调用*/
    }


    private void createView(Context context) {
        if (fView != null) {
            return;
        }
        fView = (ViewGroup) View.inflate(context, R.layout.floating_view, null);
        sFloatView = new FloatLogView(fView);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        createView(this);
        return mBinder;
    }

    /**
     * 同时接收通知栏的通知
     */
    public void initButtonReceiver() {
        mReceiver = new NotifyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(NotificationUtils.ACTION_BUTTON);
        registerReceiver(mReceiver, intentFilter);
    }

    public class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_V:
                    sFloatView.v(mSpannableBuilderWrapper.getLogs());
                    break;
                case MSG_D:
                    sFloatView.d(mSpannableBuilderWrapper.getLogs());
                    break;
                case MSG_I:
                    sFloatView.i(mSpannableBuilderWrapper.getLogs());
                    break;
                case MSG_W:
                    sFloatView.w(mSpannableBuilderWrapper.getLogs());
                    break;
                case MSG_E:
                    sFloatView.e(mSpannableBuilderWrapper.getLogs());
                    break;
            }
        }
    }

    public class NotifyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(NotificationUtils.ACTION_BUTTON)) {
                int buttonId = intent.getIntExtra(NotificationUtils.INTENT_BUTTONID_TAG, 0);
                switch (buttonId) {
                    case NotificationUtils.BUTTON_LOG_SWITCH_ID:
                        sFloatView.setFloatingLogVisiable(NotificationUtils.switchOn);
                        break;
                    case NotificationUtils.BUTTON_LOG_TOUCHABLE_ID:
                        sFloatView.setViewTouchable(NotificationUtils.touchable);
                        break;
                    case NotificationUtils.BUTTON_FILTER_V_ID:
                        LogUpdateService.flterV = NotificationUtils.flterV;
                        break;
                    case NotificationUtils.BUTTON_FILTER_D_ID:
                        LogUpdateService.flterD = NotificationUtils.flterD;
                        break;
                    case NotificationUtils.BUTTON_FILTER_I_ID:
                        LogUpdateService.flterI = NotificationUtils.flterI;

                        break;
                    case NotificationUtils.BUTTON_FILTER_W_ID:
                        LogUpdateService.flterW = NotificationUtils.flterW;

                        break;

                    default:
                        break;
                }
            }
        }
    }

    public class LocalBinder extends Binder {
        LogUpdateService getService() {
            return LogUpdateService.this;
        }

        public int v(String tag, String msg) {
            if (LogUpdateService.flterV) {
                mSpannableBuilderWrapper.appendV(tag + ":" + msg);
                mHandler.sendEmptyMessage(MSG_V);
                return MSG_V;
            } else {
                return -1;
            }
        }

        public int d(String tag, String msg) {
            if (LogUpdateService.flterD) {
                mSpannableBuilderWrapper.appendD(tag + ":" + msg);
                mHandler.sendEmptyMessage(MSG_D);
                return MSG_D;
            } else {
                return -1;
            }
        }

        public int i(String tag, String msg) {
            if (LogUpdateService.flterI) {
                mSpannableBuilderWrapper.appendI(tag + ":" + msg);
                mHandler.sendEmptyMessage(MSG_I);

                return MSG_I;
            } else {
                return -1;
            }
        }

        public int w(String tag, String msg) {
            if (LogUpdateService.flterW) {
                mSpannableBuilderWrapper.appendW(tag + ":" + msg);
                mHandler.sendEmptyMessage(MSG_W);

                return MSG_W;
            } else {
                return -1;
            }
        }

        public int e(String tag, String msg) {
            mSpannableBuilderWrapper.appendE(tag + ":" + msg);
            mHandler.sendEmptyMessage(MSG_E);
            return MSG_E;
        }

        /**
         * 过滤显示哪种log
         */
        public void filter() {

        }

        public void show(boolean isShow) {

        }
    }


}
