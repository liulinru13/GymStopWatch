package com.mmrx.gymstopwatch.timer;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mmrx.gymstopwatch.R;
import com.mmrx.gymstopwatch.util.DateTimeUtil;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class CountingTimerView extends FrameLayout {

    private static final String TWO_DIGITS_D = "%02d";
    private static final String ONE_DIGIT_D = "%01d";
    private static final String NEG_TWO_DIGITS_D = "-%02d";
    private static final String NEG_ONE_DIGIT_D = "-%01d";
    private static final String TWO_DIGITS_M = "%02d.";
    private static final String TWO_DIGITS = "%02d";
    // Hours and minutes are signed for when a timer goes past the set time and
    // thus negative
    private String mHours, mMinutes="00", mSeconds="00", mHundredths="00";

    private TextView dateTv, secondTv, millionSecTv;
    private TickerView minuteTv;
    Runnable mBlinkThread = new Runnable() {
        private boolean mVisible = true;

        @Override
        public void run() {
            mVisible = !mVisible;
            CountingTimerView.this.showTime(mVisible);
            postDelayed(mBlinkThread, 500);
        }
    };

    public CountingTimerView(Context context) {
        super(context);
    }

    public CountingTimerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountingTimerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dateTv = (TextView)findViewById(R.id.dateTime_tv);
//        minuteTv = (TextView)findViewById(R.id.minute_tv);
        minuteTv = findViewById(R.id.minute_tv);
        minuteTv.setCharacterLists(TickerUtils.provideNumberList());
        secondTv = (TextView)findViewById(R.id.second_tv);
        millionSecTv = (TextView)findViewById(R.id.million_second_tv);

        updateView();
    }

    public void blinkTimeStr(boolean blink) {
        if (blink) {
            removeCallbacks(mBlinkThread);
            post(mBlinkThread);
        } else {
            removeCallbacks(mBlinkThread);
            showTime(true);
        }
    }

    /**
     * Update the time to display. Separates that time into the hours, minutes,
     * seconds and hundredths. If update is true, the view is invalidated so
     * that it will draw again.
     * 
     * @param time
     *            new time to display - in milliseconds
     * @param showHundredths
     *            flag to show hundredths resolution
     * @param update
     *            to invalidate the view - otherwise the time is examined to see
     *            if it is within 100 milliseconds of zero seconds and when so,
     *            invalidate the view.
     */
    // TODO:showHundredths S/B attribute or setter - i.e. unchanging over object
    // life
    public void setTime(long time, boolean showHundredths, boolean update) {
        int oldLength = getDigitsLength();
        boolean neg = false, showNeg = false;
        String format;
        if (time < 0) {
            time = -time;
            neg = showNeg = true;
        }
        long hundreds, seconds, minutes, hours;
        seconds = time / 1000;
        hundreds = (time - seconds * 1000) / 10;
        minutes = seconds / 60;
        seconds = seconds - minutes * 60;
        hours = minutes / 60;
        minutes = minutes - hours * 60;
        if (hours > 999) {
            hours = 0;
        }
        // The time can be between 0 and -1 seconds, but the "truncated"
        // equivalent time of hours
        // and minutes and seconds could be zero, so since we do not show
        // fractions of seconds
        // when counting down, do not show the minus sign.
        // TODO:does it matter that we do not look at showHundredths?
        if (hours == 0 && minutes == 0 && seconds == 0) {
            showNeg = false;
        }

        // Normalize and check if it is 'time' to invalidate
        if (!showHundredths) {
            if (!neg && hundreds != 0) {
                seconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                    if (minutes == 60) {
                        minutes = 0;
                        hours++;
                    }
                }
            }
            if (hundreds < 10 || hundreds > 90) {
                update = true;
            }
        }

        if (hours >= 10) {
            format = showNeg ? NEG_TWO_DIGITS_D : TWO_DIGITS_D;
            mHours = String.format(format, hours);
        } else if (hours > 0) {
            format = showNeg ? NEG_ONE_DIGIT_D : ONE_DIGIT_D;
            mHours = String.format(format, hours);
        } else {
            format = showNeg ? NEG_ONE_DIGIT_D : ONE_DIGIT_D;
            mHours = String.format(format, 0);
        }

        // Minutes are never empty and when hours are non-empty, must be two
        // digits
        if (minutes >= 10 || hours > 0) {
            format = (showNeg && hours == 0) ? NEG_TWO_DIGITS_D : TWO_DIGITS_D;
            mMinutes = String.format(format, minutes);
        } else {
            format = (showNeg && hours == 0) ? NEG_TWO_DIGITS_D : TWO_DIGITS_D;
            mMinutes = String.format(format, minutes);
        }

        // Seconds are always two digits
        if (showHundredths) {
            mSeconds = String.format(TWO_DIGITS_M, seconds);
        } else {
            mSeconds = String.format(TWO_DIGITS, seconds);
        }
        // #endif

        // Hundredths are optional and then two digits
        if (showHundredths) {
            mHundredths = String.format(TWO_DIGITS, hundreds);
        } else {
            mHundredths = String.format(TWO_DIGITS, 0);
        }

        int newLength = getDigitsLength();
        if (oldLength != newLength) {
            if (oldLength > newLength) {
                // resetTextSize();
            }
        }
        if (update) {
            updateView();
        }
    }

    private void updateView(){
        String dateStr = DateTimeUtil.getSysTime();
        dateTv.setText(dateStr);

        minuteTv.setText(mMinutes);
        secondTv.setText(mSeconds);
        millionSecTv.setText(mHundredths);
    }

    public void showTime(boolean visible) {
        updateView();
    }

    private int getDigitsLength() {
        return ((mHours == null) ? 0 : mHours.length()) + ((mMinutes == null) ? 0 : mMinutes.length()) + ((mSeconds == null) ? 0 : mSeconds.length())
                + ((mHundredths == null) ? 0 : mHundredths.length());
    }
}
