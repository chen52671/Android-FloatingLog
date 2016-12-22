package chen.zheng.floatinglog;

import android.content.Context;
import android.graphics.PixelFormat;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;

import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class FloatLogView {
    private static final String TAG = "FloatLogView";
    private static final int MAX_TEXTVIEW_SIZE = 6000;
    ViewGroup mlayoutView;
    Context context;
    private WindowManager mWindowManager = null;
    private WindowManager.LayoutParams wmParams = null;


    private TextView floatingTextView;
    private boolean mTouchable;
    private boolean mVisiable;

    public FloatLogView(ViewGroup layoutView) {
        mlayoutView = layoutView;
        context = mlayoutView.getContext();
        initWindow();
    }

    public void initWindow() {
        mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = LayoutParams.TYPE_PHONE;
        wmParams.format = PixelFormat.RGBA_8888;

        wmParams.flags = LayoutParams.FLAG_NOT_TOUCHABLE
                | LayoutParams.FLAG_NOT_FOCUSABLE
                | LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        //wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;

        wmParams.gravity = Gravity.LEFT | Gravity.TOP;
        wmParams.x = 0;
        wmParams.y = 0;
        wmParams.width = mWindowManager.getDefaultDisplay().getWidth();
        wmParams.height = mWindowManager.getDefaultDisplay().getHeight() / 2;

        floatingTextView = (TextView) mlayoutView.findViewById(R.id.floating_tv);
        floatingTextView.setMovementMethod(ScrollingMovementMethod.getInstance());
        mWindowManager.addView(mlayoutView, wmParams);
    }

    public void setViewTouchable(boolean touchable) {
        this.mTouchable = touchable;
        if (touchable) {
            wmParams.flags &= ~LayoutParams.FLAG_NOT_TOUCHABLE;
            wmParams.flags |= LayoutParams.FLAG_NOT_TOUCH_MODAL;
        } else {
            wmParams.flags |= LayoutParams.FLAG_NOT_TOUCHABLE;
            wmParams.flags &= ~LayoutParams.FLAG_NOT_TOUCH_MODAL;
        }
        mWindowManager.updateViewLayout(mlayoutView, wmParams);
    }

    public void setFloatingLogVisiable(boolean visiable) {
        this.mVisiable = visiable;
        mlayoutView.setVisibility(visiable ? View.VISIBLE : View.GONE);
    }

    public int v(CharSequence msg) {
        Log.v(TAG, msg.toString());
        refreshTextView();

        floatingTextView.append(msg);
        return 0;
    }

    public int d(CharSequence msg) {
        Log.d(TAG, msg.toString());
        refreshTextView();

        floatingTextView.append(msg);
        return 0;
    }

    public int i(CharSequence msg) {
        Log.i(TAG, msg.toString());
        refreshTextView();

        floatingTextView.append(msg);
        return 0;
    }

    public int w(CharSequence msg) {
        Log.w(TAG, msg.toString());
        refreshTextView();

        floatingTextView.append(msg);
        return 0;
    }

    public int e(CharSequence msg) {
        Log.e(TAG, msg.toString());
        refreshTextView();

        floatingTextView.append(msg);
        return 0;
    }

    private void refreshTextView() {
        CharSequence uiText = floatingTextView.getText();
        if (uiText.length() > MAX_TEXTVIEW_SIZE) {
            floatingTextView.setText(uiText.subSequence(MAX_TEXTVIEW_SIZE / 3, uiText.length() - 1)
            );
        }

        if (!mTouchable) {
            int offset = floatingTextView.getLineCount() * floatingTextView.getLineHeight();
            if (offset > floatingTextView.getHeight()) {
                floatingTextView.scrollTo(0, offset - floatingTextView.getHeight());
            }
        }
    }
}