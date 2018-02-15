package com.developermarshall.androidlevel;

import android.content.Context;
import android.graphics.PointF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class AccelerationSensor implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private OnDataReceivedListener onDataReceivedListener;

    /**
     * This class implements the acceleration sensor
     */
    public AccelerationSensor(Context context, OnDataReceivedListener onDataReceivedListener) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.onDataReceivedListener = onDataReceivedListener;
    }

    /**
     * Unregister the accelerometer
     */
    public void stopAccelerometer() {
        sensorManager.unregisterListener(this);
    }

    /**
     * Register the accelerometer
     */
    public void startAccelerometer() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
    }

    /**
     * Receive data from the accelerometer
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            onDataReceivedListener.onDataReceived(new PointF(event.values[0], event.values[1]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { /* Empty */ }
}
