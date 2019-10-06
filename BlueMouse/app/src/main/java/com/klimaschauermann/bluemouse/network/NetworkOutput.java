package com.klimaschauermann.bluemouse.network;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class NetworkOutput {
    private Socket socket;
    private String ipAddress;

    public NetworkOutput(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void connect(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ipAddress, 11000);
                } catch (IOException e){

                }
            }
        });

        thread.start();
    }

    public void sendMouseLeftClick(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket.getOutputStream().write(0);
                } catch (IOException e){

                }
            }
        });

        thread.start();
    }

    public void sendMouseRightClick(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket.getOutputStream().write(2);
                } catch (IOException e){

                }
            }
        });

        thread.start();
    }

    public void sendMouseMove(final double dx, final double dy){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStream out = socket.getOutputStream();
                    byte[] buffer = new byte[Double.BYTES];
                    out.write(3);

                    ByteBuffer.wrap(buffer).putDouble(dx);
                    out.write(buffer);

                    ByteBuffer.wrap(buffer).putDouble(dy);
                    out.write(buffer);
                } catch (IOException e){

                }
            }
        });

        thread.start();
    }
}
