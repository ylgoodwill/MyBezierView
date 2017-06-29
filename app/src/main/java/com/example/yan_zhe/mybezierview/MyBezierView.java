package com.example.yan_zhe.mybezierview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yan_zhe on 2017/6/29.
 */

public class MyBezierView extends View {
    private int mWidth;
    private int mHeight;
    boolean isBack = false;
    Point midPoint = new Point(550, 600);
    int xx = 600;
    Point endPoint = new Point(800, xx);
    private int mFlyPercent = 100;
    private int mBitmapX;
    private int mBitmapY;

    public MyBezierView(Context context) {
        super(context);
    }

    public MyBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        if (widthMode == MeasureSpec.EXACTLY) {
//            mWidth = widthSize;
//        } else {
//            mWidth = 200;
//        }
//
//
//        if (heightMode == MeasureSpec.EXACTLY) {
//            mHeight = heightSize;
//        } else {
//            mHeight = 200;
//        }
//        setMeasuredDimension(mWidth, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.init);
        Bitmap backgroundBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20);
        Path path = new Path();
        Point end = new Point(800, xx);

        canvas.drawBitmap(backgroundBitmap, 200, 500, paint);
        paint.setColor(Color.BLUE);
        // canvas.drawCircle(300, end.y, 10, paint);
        // canvas.drawCircle(end.x, end.y, 10, paint);
        if (isBack) {
            paint.setColor(Color.TRANSPARENT);
            path.moveTo(300, 600);
            path.quadTo(midPoint.x, xx + (midPoint.y - xx) * mFlyPercent / 100, endPoint.x, endPoint.y);
            canvas.drawPath(path, paint);

            if (mFlyPercent > 0) {
                paint.setColor(Color.RED);
                canvas.drawBitmap(bitmap, mBitmapX, mBitmapY * mFlyPercent / 100, paint);
                mFlyPercent -= 5;
                //postInvalidateDelayed(5);
                invalidate();
            } else {
                paint.setColor(Color.RED);
                mFlyPercent = 100;
                isBack = false;
                canvas.drawBitmap(bitmap, midPoint.x - bitmap.getWidth() / 2, 550 - bitmap.getHeight() / 2, paint);
            }

        } else {
            paint.setColor(Color.TRANSPARENT);
            path.moveTo(300, xx);
            path.quadTo(midPoint.x, midPoint.y, endPoint.x, endPoint.y);
            canvas.drawPath(path, paint);
            mBitmapX = midPoint.x - bitmap.getWidth() / 2;
            mBitmapY = (midPoint.y - xx) / 2 + xx - bitmap.getHeight();
            paint.setColor(Color.RED);
            canvas.drawBitmap(bitmap, mBitmapX, mBitmapY, paint);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                isBack = true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getY() > 600)
                    midPoint.y = (int) event.getY();
                invalidate();
                break;
        }
        return true;
    }
}
