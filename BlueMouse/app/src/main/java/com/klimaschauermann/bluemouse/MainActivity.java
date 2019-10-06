package com.klimaschauermann.bluemouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.klimaschauermann.bluemouse.network.InputHandler;

public class MainActivity extends AppCompatActivity {
    private InputHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = InputHandler.getInstance();
    }


}
