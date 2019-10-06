package com.klimaschauermann.bluemouse.network;

import android.util.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class NetworkOutput {
    private Socket socket;
    private String ipAddress;

    public NetworkOutput(String ipAddress) {
        this.ipAddress = ipAddress;

        connect();
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

    public void sendMouseMove(final float dx, final float dy){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OutputStream out = socket.getOutputStream();
                    byte[] buffer = new byte[4];
                    out.write(3);

                    buffer = intToByteArray(Math.round(dx));
                    Log.d("dx", dx + ": " + bufferToString(buffer));
                    out.write(buffer);

                    buffer = intToByteArray(Math.round(dy));
                    Log.d("dy", dy + ": " + bufferToString(buffer));
                    out.write(buffer);
                    Log.d("pause","pause");
                } catch (IOException e){

                }
            }
        });

        thread.start();
    }

    private String bufferToString(byte[] buffer){
        StringBuilder builder = new StringBuilder('[');
        for (byte spot: buffer) {
            builder.append(spot).append(", ");
        }
        builder.append(']');
        return builder.toString();

    }

    public static final byte[] intToByteArray(int value) {
        return new byte[] {
                (byte)(value >>> 24),
                (byte)(value >>> 16),
                (byte)(value >>> 8),
                (byte)value};
    }

}
