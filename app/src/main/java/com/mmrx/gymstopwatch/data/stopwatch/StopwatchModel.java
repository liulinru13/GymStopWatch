package com.mmrx.gymstopwatch.data.stopwatch;

import android.content.Context;

/**
 * All {@link Stopwatch} data is accessed via this model.
 */
public class StopwatchModel {

    private final Context mContext;

    /** The current state of the stopwatch. */
    private Stopwatch mStopwatch;

    StopwatchModel(Context context, NotificationModel notificationModel) {
        mContext = context;
    }

    /**
     * @param stopwatch
     *            the new state of the stopwatch
     */
    Stopwatch setStopwatch(Stopwatch stopwatch) {
        if (mStopwatch != stopwatch) {
            StopwatchDAO.setStopwatch(mContext, stopwatch);
            mStopwatch = stopwatch;
        }

        return stopwatch;
    }

    /**
     * @return the current state of the stopwatch
     */
    Stopwatch getStopwatch() {
        if (mStopwatch == null) {
            mStopwatch = StopwatchDAO.getStopwatch(mContext);
        }

        return mStopwatch;
    }
}
