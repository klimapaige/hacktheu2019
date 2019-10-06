package com.klimaschauermann.bluemouse;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.view.MotionEvent;

import com.klimaschauermann.bluemouse.network.InputHandler;

public class MouseView extends View {
    private Path drawPath;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private InputHandler inputHandler;


    public MouseView(Context context, AttributeSet attrs){
        super(context, attrs);
        setUpMouseView();
    }

    private void setUpMouseView(){
        drawPath = new Path();
        inputHandler = InputHandler.getInstance();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        Log.d("current",touchX+", "+touchY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                inputHandler.moveDown(touchX,touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                inputHandler.moved(touchX,touchY);
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                return false;
        }
        return true;
    }
}
