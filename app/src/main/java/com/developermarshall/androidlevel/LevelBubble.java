package com.developermarshall.androidlevel;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class LevelBubble extends View {

    private float positionAdjustmentPercentX;
    private float positionAdjustmentPercentY;
    private float levelHeightPixels;
    private float levelWidthPixels;
    private float prevX = .5f;
    private float prevY = .5f;
    private final int ANIMATION_DURATION = 100;

    /**
     * This class represents the bubble that indicates the device's tilt.
     */
    public LevelBubble(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Convert percent coordinates to pixel coordinates
     *
     * @param coordsInPercent - the percent coordinates
     * @return the pixel coordinates
     */
    private PointF getCoordsInPixels(PointF coordsInPercent){
        PointF pixelValues = new PointF();
        pixelValues.x = (coordsInPercent.x - positionAdjustmentPercentX) * levelWidthPixels;
        pixelValues.y = (coordsInPercent.y - positionAdjustmentPercentY) * levelHeightPixels;
        return pixelValues;
    }

    /**
     * Move the bubble to new coordinates
     *
     * @param coordsInPercent the new coordinates
     */
    public void moveBubble(PointF coordsInPercent){
        PointF pixelValues = getCoordsInPixels(coordsInPercent);
        float x = pixelValues.x;
        float y = pixelValues.y;
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "translationX", prevX, x);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "translationY", prevY, y);
        animatorX.setDuration(ANIMATION_DURATION);
        animatorY.setDuration(ANIMATION_DURATION);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animatorX, animatorY);
        prevX = x;
        prevY = y;
        animatorSet.start();
    }

    /**
     * Calculate adjustment that accounts for the bubble's size. This is the radii
     * of the bubble expressed as a percentage of the level's width or height.
     * Also set levelWidthPixels and levelHeightPixels.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup parent = (ViewGroup) getParent();
        float bubbleWidthPixels = (float) MeasureSpec.getSize(widthMeasureSpec);
        float bubbleHeightPixels = (float) MeasureSpec.getSize(heightMeasureSpec);
        levelWidthPixels = (float) parent.getWidth();
        if (bubbleWidthPixels > 0 && levelWidthPixels > 0) {
            positionAdjustmentPercentX = bubbleWidthPixels / levelWidthPixels / 2.0f;
        }
        levelHeightPixels = (float) parent.getHeight();
        if (bubbleHeightPixels > 0 && levelHeightPixels > 0) {
            positionAdjustmentPercentY = bubbleHeightPixels / levelHeightPixels / 2.0f;
        }
    }
}