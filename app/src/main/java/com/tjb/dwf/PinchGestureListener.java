package com.tjb.dwf;

import android.util.Log;
import android.view.ScaleGestureDetector;

public class PinchGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private ViewController viewController;

    private static final int THRESHOLD = 9;
    private int positiveCount;
    private int negativeCount;

    public PinchGestureListener(ViewController viewController) {
        super();
        this.viewController = viewController;

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
            viewController.showPicture();
        } else if (negativeCount > THRESHOLD) {
            viewController.showOptions();
        }

        return true;
    }
}
