package com.developermarshall.androidlevel;

import android.graphics.PointF;

/**
 * Implement this to receive data from the accelerometer
 */
public interface OnDataReceivedListener {

    /**
     * Data has been received from the accelerometer
     */
    void onDataReceived(PointF accelerationValues);

}
