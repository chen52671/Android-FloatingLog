package chen.zheng.floatinglog;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.RemoteViews;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationUtils {
    private static NotificationUtils sInstance;
    public final static String ACTION_BUTTON = "com.notifications.intent.action.ButtonClick";
    public final static String INTENT_BUTTONID_TAG = "ButtonId";

    public final static int BUTTON_LOG_SWITCH_ID = 1;
    public final static int BUTTON_LOG_TOUCHABLE_ID = 2;
    public final static int BUTTON_FILTER_V_ID = 3;
    public final static int BUTTON_FILTER_D_ID = 4;
    public final static int BUTTON_FILTER_I_ID = 5;
    public final static int BUTTON_FILTER_W_ID = 6;
    private NotificationManager mNotificationManager;
    public ButtonBroadcastReceiver bReceiver;
    private Context mContext;

    public static volatile boolean switchOn = true;
    public  static volatile boolean touchable = false;
    public  static volatile boolean flterV = true;
    public  static volatile boolean flterD = true;
    public  static volatile boolean flterI = true;
    public  static volatile boolean flterW = true;

    private NotificationUtils() {
    }

    public static synchronized NotificationUtils getInstance() {
        if (sInstance == null) {
            sInstance = new NotificationUtils();
        }
        return sInstance;
    }

    public void init(Context context) {
        this.mContext = context;
        initButtonReceiver();

    }

    public void showButtonNotify() {
        mNotificationManager = (NotificationManager) mContext.getSystemService(NOTIFICATION_SERVICE);

        NotificationCompat.Builder mBuilder = new Builder(mContext);
        RemoteViews mRemoteViews = new RemoteViews(mContext.getPackageName(), R.layout.view_custom_button);


        mRemoteViews.setImageViewResource(R.id.log_switch, switchOn ? R.drawable.switch_thumb_enable : R.drawable.switch_thumb_disable);
        mRemoteViews.setImageViewResource(R.id.touchable_checkbox, touchable ? R.drawable.ic_choose : R.drawable.ic_choose_off);
        mRemoteViews.setImageViewResource(R.id.filter_v_checkbox, flterV ? R.drawable.ic_choose : R.drawable.ic_choose_off);
        mRemoteViews.setImageViewResource(R.id.filter_d_checkbox, flterD ? R.drawable.ic_choose : R.drawable.ic_choose_off);
        mRemoteViews.setImageViewResource(R.id.filter_i_checkbox, flterI ? R.drawable.ic_choose : R.drawable.ic_choose_off);
        mRemoteViews.setImageViewResource(R.id.filter_w_checkbox, flterW ? R.drawable.ic_choose : R.drawable.ic_choose_off);


        //Switch点击的事件处理
        Intent buttonIntent = new Intent(ACTION_BUTTON);
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_LOG_SWITCH_ID);
        PendingIntent intent_switch = PendingIntent.getBroadcast(mContext, BUTTON_LOG_SWITCH_ID, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.log_switch, intent_switch);
       /**/
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_LOG_TOUCHABLE_ID);
        PendingIntent intent_touchable = PendingIntent.getBroadcast(mContext, BUTTON_LOG_TOUCHABLE_ID, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.touchable_checkbox, intent_touchable);

		/**/
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_FILTER_V_ID);
        PendingIntent intent_flter_V = PendingIntent.getBroadcast(mContext, BUTTON_FILTER_V_ID, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.filter_v_checkbox, intent_flter_V);
/**/
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_FILTER_D_ID);
        PendingIntent intent_flter_D = PendingIntent.getBroadcast(mContext, BUTTON_FILTER_D_ID, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.filter_d_checkbox, intent_flter_D);
        /**/
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_FILTER_I_ID);
        PendingIntent intent_flter_I = PendingIntent.getBroadcast(mContext, BUTTON_FILTER_I_ID, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.filter_i_checkbox, intent_flter_I);
        /**/
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_FILTER_W_ID);
        PendingIntent intent_flter_W = PendingIntent.getBroadcast(mContext, BUTTON_FILTER_W_ID, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.filter_w_checkbox, intent_flter_W);


        mBuilder.setContent(mRemoteViews)
                .setContentIntent(getDefalutIntent(mContext))
                .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                .setTicker("正在播放")
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_choose);
        Notification notify = mBuilder.build();
        notify.flags = Notification.FLAG_ONGOING_EVENT;
        mNotificationManager.notify(200, notify);
    }

    private PendingIntent getDefalutIntent(Context context) {
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        context,
                        11,
                        new Intent(),
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        return notifyPendingIntent;
    }

    /**
     * 带按钮的通知栏点击广播接收
     */
    public void initButtonReceiver() {
        bReceiver = new ButtonBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BUTTON);
        mContext.registerReceiver(bReceiver, intentFilter);
    }


    public class ButtonBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(ACTION_BUTTON)) {
                int buttonId = intent.getIntExtra(INTENT_BUTTONID_TAG, 0);
                switch (buttonId) {
                    case BUTTON_LOG_SWITCH_ID:
                        switchOn = !switchOn;
                        break;
                    case BUTTON_LOG_TOUCHABLE_ID:
                        touchable = !touchable;

                        break;
                    case BUTTON_FILTER_V_ID:
                        flterV = !flterV;

                        break;
                    case BUTTON_FILTER_D_ID:
                        flterD = !flterD;

                        break;
                    case BUTTON_FILTER_I_ID:
                        flterI = !flterI;

                        break;
                    case BUTTON_FILTER_W_ID:
                        flterW = !flterW;

                        break;

                    default:
                        break;
                }
                showButtonNotify();
            }
        }
    }

}
