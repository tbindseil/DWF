package com.tjb.dwf.main;

import android.view.ScaleGestureDetector;

import com.tjb.dwf.main.PinchGestureReceiver;

import javax.inject.Inject;

public class PinchGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private final PinchGestureReceiver pinchGestureReceiver;

    private static final int THRESHOLD = 9;
    private int positiveCount;
    private int negativeCount;

    @Inject
    public PinchGestureListener(PinchGestureReceiver pinchGestureReceiver) {
        super();

        this.pinchGestureReceiver = pinchGestureReceiver;

        positiveCount = 0;
        negativeCount = 0;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        positiveCount = 0;
        negativeCount = 0;
        return true;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();

        if (scaleFactor > 1.0) {
            ++positiveCount;
            negativeCount = 0;
        } else if (scaleFactor < 1.0) {
            ++negativeCount;
            positiveCount = 0;
        }

        if (positiveCount > THRESHOLD) {
            pinchGestureReceiver.showPicture();
        } else if (negativeCount > THRESHOLD) {
            pinchGestureReceiver.showOptions();
        }

        return true;
    }
}
