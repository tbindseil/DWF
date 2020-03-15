package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class MainActivity extends AppCompatActivity {
    private ViewController viewController;
    private ScaleGestureDetector scaleGestureDetector;

    private ConstraintLayout pictureLayout;
    private ConstraintLayout optionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pictureLayout = findViewById(R.id.pictureLayout);
        optionsLayout = findViewById(R.id.optionsLayout);

        showPicture();

        viewController = new ViewController(this);
        PinchGestureListener pinchGestureListener = new PinchGestureListener(viewController);
        scaleGestureDetector = new ScaleGestureDetector(this, pinchGestureListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean isPinch = scaleGestureDetector.onTouchEvent(ev);

        return isPinch ? true : super.onTouchEvent(ev);
    }

    private boolean optionsShowing() {
        return pictureLayout.indexOfChild(optionsLayout) >= 0;
    }

    public void showPicture() {
        if (optionsShowing()) {
            pictureLayout.removeView(optionsLayout);
        }
    }

    public void showOptions() {
        if (!optionsShowing()) {
            pictureLayout.addView(optionsLayout);
            pictureLayout.bringChildToFront(optionsLayout);
        }
    }
}
