package com.klimaschauermann.bluemouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.klimaschauermann.bluemouse.network.InputHandler;

public class MainActivity extends AppCompatActivity {
    private InputHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = InputHandler.getInstance();

        final Button leftButton = findViewById(R.id.leftButton);
        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handler.leftButtonClicked();
            }
        });

        final Button rightButton = findViewById(R.id.rightButton);
        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handler.rightButtonClicked();
            }
        });

    }




}
