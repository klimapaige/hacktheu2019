package com.klimaschauermann.bluemouse.network;

import android.util.Log;

public class InputHandler {
    private boolean leftButtonDown;
    private boolean rightButtonDown;
    private float lastTouchX;
    private float lastTouchY;
    private NetworkOutput networkOutput;

    private static InputHandler inputHandler;

    private InputHandler(){
        leftButtonDown = rightButtonDown = false;
        lastTouchX = lastTouchY = 0;
        networkOutput = new NetworkOutput("172.20.17.129");
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
        float dx = touchX-lastTouchX;
        float dy = touchY-lastTouchY;
        Log.d("coordinates changed",dx+" , "+dy);
        decideCall(dx,dy);
        moveDown(touchX,touchY);
    }

    private void decideCall(float dx, float dy){
        if(leftButtonDown){
            //left and move
        }else if(rightButtonDown){
            //right and move
        }else {
            networkOutput.sendMouseMove(dx,dy);
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
        networkOutput.sendMouseLeftClick();
    }
    public void rightButtonClicked(){
        networkOutput.sendMouseRightClick();
    }





}
