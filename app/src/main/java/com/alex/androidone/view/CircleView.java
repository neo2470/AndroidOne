package com.alex.androidone.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.alex.androidone.entity.Circle;

/**
 * Created by alex on 16-5-3.
 */
public class CircleView extends View {

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (null != circle) {
            canvas.drawCircle(getMeasuredWidth() >> 1, getMeasuredHeight() >> 1, circle.getRadius(), pen);
        }
    }

    public void playAnim() {
        ValueAnimator valueAnim = ValueAnimator.ofObject(new CircleEvaluator(), new Circle(10), new Circle(50));
        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circle = (Circle) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnim.setInterpolator(new BounceInterpolator());
        valueAnim.setDuration(1000);
        valueAnim.start();
    }

    private void initialize() {
        pen = new Paint();
        pen.setAntiAlias(true);
        pen.setColor(Color.RED);
        pen.setStyle(Paint.Style.FILL);

        circle = new Circle(10);
    }

    private class CircleEvaluator implements TypeEvaluator<Circle> {

        @Override
        public Circle evaluate(float fraction, Circle startValue, Circle endValue) {
            int radius = (int) (startValue.getRadius() + fraction * (endValue.getRadius() - startValue.getRadius()));
            return new Circle(radius);
        }
    }

    public int getCircleRadius() {
        return circle.getRadius();
    }

    public void setCircleRadius(int radius) {
        circle.setRadius(radius);
        invalidate();
    }

    private Paint pen;
    private Circle circle;
}
