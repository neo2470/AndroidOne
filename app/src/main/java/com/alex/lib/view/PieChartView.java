package com.alex.lib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by alex on 2018/3/27.
 * 繪製餅狀圖
 */

public class PieChartView extends View {

    public PieChartView(Context context) {
        super(context);
        init();
    }

    public PieChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 添加扇區
     * @param percent 扇區佔比
     * @param color 扇區填充色
     */
    public void addSelect(float percent, int color) {
        if (1.0f >= perecentLimit + percent) {
            mSectors.add(new Sector(percent, color));
            perecentLimit += percent;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mOval.set(0, 0, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (null != mSectors) {
            startAngle = 0;
            for (Sector s : mSectors) {
                mPen.setColor(s.color);
                canvas.drawArc(mOval, startAngle, s.degree, true, mPen);
                startAngle += s.degree;
            }
        }
    }

    /**
     * 初始化配置
     */
    private void init() {
        perecentLimit = 0;
        startAngle =  0;

        mPen = new Paint();
        mPen.setAntiAlias(true);
        mPen.setStyle(Paint.Style.FILL);

        mOval = new RectF();

        mSectors = new ArrayList<>();

        test();
    }

    private void test() {
        addSelect(0.3f, Color.RED);
        addSelect(0.1f, Color.BLUE);
        addSelect(0.4f, Color.GREEN);
        addSelect(0.2f, Color.GRAY);
    }

    private float perecentLimit;// 扇形佔比統計
    private float startAngle;// 繪製起始角度

    private Paint mPen;// 畫筆
    private RectF mOval;// 餅狀圖所在橢圓區域
    private ArrayList<Sector> mSectors;// 繪製扇區集合

    /**
     * 扇形區域數據描述
     */
    private class Sector {
        public Sector(float percent, int color) {
            this.percent = percent;
            degree = percent * 360;
            this.color = color;
        }

        private float degree;// 扇形跨越的角度
        private float percent;// 扇形佔比
        private int color;// 扇形填充顏色
    }
}
