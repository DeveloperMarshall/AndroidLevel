package com.developermarshall.androidlevel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class LevelSurfaceRelativeLayout extends RelativeLayout {

    private Paint paint;
    private Path path;

    /**
     * This is the parent layout for the Bubble.
     */
    public LevelSurfaceRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setWillNotDraw(false);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        path = new Path();
    }

    /**
     * Draw the axes.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = canvas.getWidth();
        float height = canvas.getHeight();
        path.moveTo(0, height / 2);
        path.lineTo(width, height / 2);
        path.moveTo(width / 2, 0);
        path.lineTo(width / 2, height);
        canvas.drawPath(path, paint);
    }
}