package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

// steps:
// 1) login with 2nd tutorial from today
// 2) do that at the start
// 3) store and reuse jwt in single session
// 4) store and reuse jwt in multiple sessions (not encrypted)
// 5) store and reuse jwt in multiple sessions, encrypted
// 6) research token expiration
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

    public void onClickCreatePicture(View v) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
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
