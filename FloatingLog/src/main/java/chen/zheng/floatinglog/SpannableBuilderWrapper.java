package chen.zheng.floatinglog;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.SpannedString;
import android.text.style.ForegroundColorSpan;

public class SpannableBuilderWrapper {


    SpannableStringBuilder mBuilder = new SpannableStringBuilder("Start! \n");

    public SpannableBuilderWrapper() {
    }

    public CharSequence getLogs() {
        CharSequence logs = new SpannedString(mBuilder);
        mBuilder.clearSpans();
        mBuilder.clear();
        return logs;
    }

    public void appendV(String msg) {
        String finalMsg = msg + "\n";
        SpannableStringBuilder builder = new SpannableStringBuilder(finalMsg);
        ForegroundColorSpan whiteSpan = new ForegroundColorSpan(Color.WHITE);
        builder.setSpan(whiteSpan, 0, finalMsg.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mBuilder.append(builder);
    }

    public void appendD(String msg) {
        String finalMsg = msg + "\n";
        SpannableStringBuilder builder = new SpannableStringBuilder(finalMsg);
        ForegroundColorSpan blueSpan = new ForegroundColorSpan(Color.BLUE);

        builder.setSpan(blueSpan, 0, finalMsg.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mBuilder.append(builder);
    }

    public void appendI(String msg) {
        String finalMsg = msg + "\n";
        SpannableStringBuilder builder = new SpannableStringBuilder(finalMsg);
        ForegroundColorSpan greenSpan = new ForegroundColorSpan(Color.GREEN);
        builder.setSpan(greenSpan, 0, finalMsg.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mBuilder.append(builder);
    }

    public void appendW(String msg) {
        String finalMsg = msg + "\n";
        SpannableStringBuilder builder = new SpannableStringBuilder(finalMsg);
        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(Color.YELLOW);
        builder.setSpan(yellowSpan, 0, finalMsg.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mBuilder.append(builder);
    }

    public void appendE(String msg) {
        String finalMsg = msg + "\n";
        SpannableStringBuilder builder = new SpannableStringBuilder(finalMsg);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, 0, finalMsg.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mBuilder.append(builder);
    }

}
