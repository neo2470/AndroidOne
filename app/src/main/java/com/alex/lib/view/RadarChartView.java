package com.alex.lib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by alex on 2018/3/27.
 * 繪製雷達圖
 */

public class RadarChartView extends View {

    public RadarChartView(Context context) {
        super(context);
        init();
    }

    public RadarChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 新增指標
     * @param name 指標名稱
     * @param percent 指標佔比
     */
    public void addQuota(String name, float percent) {
        if (1.0f < percent) {
            return;
        }

        mQuotas.add(new Quota(name, percent));
        quota = mQuotas.size();
        radian = (float) (2 * Math.PI / quota);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        center.set(w>>1, h>>1);
        radius = Math.min(w, h) * 0.45f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 繪製多邊形
        drawPolygon(canvas);

        drawLines(canvas);

        drawRegon(canvas);
    }

    /**
     * 繪製多邊形
     * @param canvas 畫板
     */
    private void drawPolygon(Canvas canvas) {
//        canvas.drawCircle(center.x, center.y, radius, mPen);
        mPen.setColor(Color.GRAY);
        mPen.setStyle(Paint.Style.STROKE);
        float R = radius / level;
        for (int i = 1; i <= level; ++i) {
            float r = R * i;
            mPath.reset();
            for (int j = 0; j < quota; ++j) {
                if (0 == j) {
                    mPath.moveTo(center.x + r, center.y);
                } else {
                    float x = (float) (center.x + Math.cos(radian * j) * r);
                    float y = (float) (center.y + Math.sin(radian * j) * r);
                    mPath.lineTo(x, y);
                }
            }
            mPath.close();
            canvas.drawPath(mPath, mPen);
        }
    }

    /**
     * 繪製圓心到邊緣的線段
     * @param canvas 畫板
     */
    private void drawLines(Canvas canvas) {
        for (int i = 0; i < quota; ++i) {
            float x = (float) (center.x + Math.cos(radian * i) * radius);
            float y = (float) (center.y + Math.sin(radian * i) * radius);
            canvas.drawLine(center.x, center.y, x, y, mPen);
        }
    }

    private void drawRegon(Canvas canvas) {
        mPath.reset();
        mPen.setColor(Color.BLUE);
        mPen.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mQuotas.size(); ++i) {
            Quota q = mQuotas.get(i);

            float x = (float) (center.x + Math.cos(radian * i) * radius * q.percent);
            float y = (float) (center.y + Math.sin(radian * i) * radius * q.percent);

            Log.d("Debug", "drawRegon x = " + x + ", y = " + y);

            if (0 == i) {
                mPath.moveTo(x, center.y);
            } else {
                mPath.lineTo(x, y);
            }

            canvas.drawCircle(x, y, radius * 0.03f, mPen);
        }

        mPath.close();


        mPen.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath, mPen);

        mPen.setAlpha(127);
        mPen.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(mPath, mPen);
    }

    private void init() {

        center = new Point();

        mQuotas = new ArrayList<>();

        mPen = new Paint();
        mPen.setAntiAlias(true);
        mPen.setStyle(Paint.Style.STROKE);
        mPen.setColor(Color.GRAY);

        mPath = new Path();

        test();
    }

    private void test() {
        addQuota("A", 0.7f);
        addQuota("B", 0.9f);
        addQuota("C", 0.4f);
        addQuota("D", 1.0f);
        addQuota("E", 0.8f);
        addQuota("F", 0.9f);
        level = 3;
    }

    private int quota;// 指標個數
    private int level;// 等級數
    private float radius;// 雷達圖半徑
    private float radian;// 每個指標對應弧度

    private Point center;// 中心點
    private ArrayList<Quota> mQuotas;// 指標數據

    private Paint mPen;
    private Path mPath;

    /**
     * 指標數據封裝
     */
    private class Quota {

        public Quota(String name, float percent) {
            this.name = name;
            this.percent = percent;
        }

        private String name;
        private float percent;
    }
}
