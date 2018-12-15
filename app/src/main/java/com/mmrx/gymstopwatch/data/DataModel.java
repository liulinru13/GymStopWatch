package com.mmrx.gymstopwatch.data;


import android.content.Context;

import static com.mmrx.gymstopwatch.util.Utils.enforceMainLooper;

public class DataModel {

    private Context mContext;
    /**
     * The single instance of this data model that exists for the life of the
     * application.
     */
    private static final DataModel sDataModel = new DataModel();

    /** The model from which stopwatch data are fetched. */
    private StopwatchModel mStopwatchModel;

    /**
     * The context may be set precisely once during the application life.
     */
    public void setContext(Context context) {
        this.mContext = context;
        mStopwatchModel = new StopwatchModel(mContext, null);

    }

    public static DataModel getDataModel() {
        return sDataModel;
    }

    /**
     * @return the stopwatch after being started
     */
    public Stopwatch startStopwatch() {
        enforceMainLooper();
        return mStopwatchModel.setStopwatch(getStopwatch().start());
    }

    /**
     * @return the stopwatch after being paused
     */
    public Stopwatch pauseStopwatch() {
        enforceMainLooper();
        return mStopwatchModel.setStopwatch(getStopwatch().pause());
    }
    
    /**
     * @return the stopwatch after being reset
     */
    public Stopwatch resetStopwatch() {
        enforceMainLooper();
        return mStopwatchModel.setStopwatch(getStopwatch().reset());
    }

    /**
     * @return the current state of the stopwatch
     */
    public Stopwatch getStopwatch() {
        enforceMainLooper();
        return mStopwatchModel.getStopwatch();
    }
}
