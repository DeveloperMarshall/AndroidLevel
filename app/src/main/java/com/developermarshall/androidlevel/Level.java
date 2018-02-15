package com.developermarshall.androidlevel;

import android.content.Context;
import android.graphics.PointF;
import android.os.Handler;

public class Level implements OnDataReceivedListener {

    private AccelerationSensor accelerationSensor;
    private static final float GRAVITY = 9.81f;
    private static final float ALPHA = 0.97f;
    private float prevX = .5f;
    private float prevY = .5f;
    private float accelerationX;
    private float accelerationY;
    private PointF percentCoords;
    private Handler bubbleHandler;
    private LevelBubble levelBubble;

    /**
     * This class represents the level that holds the bubble.
     */
    public Level(Context context, LevelBubble levelBubble){
        this.levelBubble = levelBubble;
        accelerationSensor = new AccelerationSensor(context, this);
        percentCoords = new PointF();
    }

    /**
     * Start the acceleration sensor and the bubble's motion.
     */
    public void start(){
        accelerationSensor.startAccelerometer();
        bubbleHandler = new Handler();
        bubbleHandler.post(bubbleRunnable);
    }

    /**
     * Stop the acceleration sensor and the bubble's motion.
     */
    public void stop(){
        bubbleHandler.removeCallbacks(bubbleRunnable);
        accelerationSensor.stopAccelerometer();
    }

    /**
     * Filter the values from the acceleration sensor
     *
     * @param accelerationValues the values to filter
     * @return the filtered values
     */
    private PointF getFilteredAccelerationValues(PointF accelerationValues) {
        accelerationX = ALPHA * accelerationX + (1 - ALPHA) * accelerationValues.x;
        accelerationY = ALPHA * accelerationY + (1 - ALPHA) * accelerationValues.y;
        PointF filteredAccelerationValues = new PointF();
        filteredAccelerationValues.set(accelerationX, accelerationY);
        return filteredAccelerationValues;
    }

    /**
     * Convert acceleration values to percent coordinates and set percentCoords
     *
     * @param accelerationValues the acceleration values to convert
     */
    private void setCoordsInPercent(PointF accelerationValues) {
        float x = .5f + accelerationValues.x / GRAVITY / 2f;
        float y = .5f - accelerationValues.y / GRAVITY / 2f;
        percentCoords.set(x, y);
    }


    @Override
    public void onDataReceived(PointF accelerationValues) {
        PointF filteredAccelerationValues = getFilteredAccelerationValues(accelerationValues);
        setCoordsInPercent(filteredAccelerationValues);
    }

    /**
     * Runnable that controls the bubble's motion
     */
    Runnable bubbleRunnable = new Runnable(){

        @Override
        public void run() {
            levelBubble.moveBubble(percentCoords);
            bubbleHandler.postDelayed(this,100);
        }
    };
}
