package com.klimaschauermann.bluemouse.network;

import android.util.Log;

public class InputHandler {
    private boolean leftButtonDown;
    private boolean rightButtonDown;
    private float lastTouchX;
    private float lastTouchY;

    private static InputHandler inputHandler;

    private InputHandler(){
        leftButtonDown = rightButtonDown = false;
        lastTouchX = lastTouchY = 0;
    }

    public static InputHandler getInstance(){
        if(inputHandler == null){
            inputHandler = new InputHandler();
        }
        return  inputHandler;
    }

    public void moveDown(float touchX, float touchY){
        lastTouchX=touchX;
        lastTouchY=touchY;
        Log.d("coordinates set",touchX+" , "+touchY);
    }

    public void moved(float touchX, float touchY){
        float dx = lastTouchX-touchX;
        float dy = lastTouchY-touchY;
        Log.d("coordinates changed",dx+" , "+dy);
        //call movement
        moveDown(touchX,touchY);
    }

    private void decideCall(float dx, float dy){
        if(leftButtonDown){
            //left and move
        }else if(rightButtonDown){
            //right and move
        }else {
            //move
        }
    }


    public void leftButtonPressed(){
        leftButtonDown=true;
    }
    public void rightButtonPressed(){
        rightButtonDown=true;
    }
    public void leftButtonReleased(){
        leftButtonDown=false;
    }
    public void rightButtonReleased(){
        rightButtonDown=false;
    }
    public void leftButtonClicked(){

    }
    public void rightButtonClicked(){

    }





}
