package com.alex.androidone.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by alex on 2018/3/27.
 * 自定義View學習
 */

public class CustomView extends View {

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getSize(100, widthMeasureSpec);
        int height = getSize(100, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private int getSize(int defaultSize, int sizeMeasureSpec) {
        int mySize = defaultSize;
        int mode = MeasureSpec.getMode(sizeMeasureSpec);
        int size = MeasureSpec.getSize(sizeMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED : {
                mySize = defaultSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                mySize = size;
                break;
            }
            case MeasureSpec.EXACTLY: {
                mySize = size;
                break;
            }
        }
        return mySize;
    }
}
