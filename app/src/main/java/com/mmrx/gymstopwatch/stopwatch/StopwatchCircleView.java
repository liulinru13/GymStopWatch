package com.mmrx.gymstopwatch.stopwatch;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.mmrx.gymstopwatch.data.DataModel;
import com.mmrx.gymstopwatch.data.Stopwatch;

public class StopwatchCircleView extends View {

    public StopwatchCircleView(Context context) {
        super(context);
    }

    public StopwatchCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StopwatchCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Stopwatch stopwatch = getStopwatch();
        // If the stopwatch is not running it does not require continuous
        // updates.
        if (stopwatch.isRunning()) {
            postInvalidateOnAnimation();
        }
    }

    /**
     * Start the animation if it is not currently running.
     */
    public void update() {
        postInvalidateOnAnimation();
    }

    private Stopwatch getStopwatch() {
        return DataModel.getDataModel().getStopwatch();
    }
}
