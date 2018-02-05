package com.jzb.android.widget.showcase.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.jzb.android.widget.showcase.Target;

/**
 * Created by wikipeng on 2017/6/16.
 */

public class ShowcaseShapeRectangle {
    private Target target;
    private int padding = 20;
    private boolean isRoundCornerHalf;

    public ShowcaseShapeRectangle(Target target, int padding) {
        this.target = target;
        this.padding = padding;
        //        circlePoint = getFocusPoint();
        //        calculateRadius(padding);
    }

    //    public void draw(Canvas canvas, Paint eraser, int padding) {
    ////        calculateRadius(padding);
    ////        circlePoint = getFocusPoint();
    ////        canvas.drawCircle(circlePoint.x, circlePoint.y, radius, eraser);
    //    }

    public void draw(Canvas canvas, Paint eraser, int padding) {
        if (isRoundCornerHalf) {
            int height = target.getRect().height();
            int width  = target.getRect().width();

            int roundCorner = Math.min(height / 2, width / 2);

            canvas.drawRoundRect(target.getRectF(), roundCorner, roundCorner, eraser);
        } else {
            canvas.drawRect(target.getRect(), eraser);
        }
    }

    public void setRoundCornerHalf(boolean isRoundCornerHalf) {
        this.isRoundCornerHalf = isRoundCornerHalf;
    }

}
