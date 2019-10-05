package com.klimaschauermann.bluemouse;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.view.MotionEvent;

public class MouseView extends View {
    private Path drawPath;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private boolean leftButtonDown;
    private boolean rightButtonDown;
    private float lastTouchX;
    private float lastTouchY;

    public MouseView(Context context, AttributeSet attrs){
        super(context, attrs);
        setUpMouseView();
    }

    private void setUpMouseView(){
        drawPath = new Path();
        leftButtonDown = rightButtonDown = false;
        lastTouchX = lastTouchY = 0;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Log.d("data", ;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        Log.d("current",touchX+", "+touchY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d("start","Finger moved down");
                lastTouchX=touchX;
                lastTouchY=touchY;
                drawPath.moveTo(touchX, touchY);
                Log.d("position placed","path successfully moved to location");
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                Log.d("done","Finger moved up");
                drawPath.reset();
                break;
            default:
                return false;
        }
        return true;
    }
}
