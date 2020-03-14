package com.tjb.dwf;

import android.view.ScaleGestureDetector;

public class PinchGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
    private ViewController viewController;

    public PinchGestureListener(ViewController viewController) {
        super();
        this.viewController = viewController;
    }

    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        float scaleFactor = detector.getScaleFactor();

        if (scaleFactor > 1.0) {
            viewController.showPicture();
        } else if (scaleFactor < 1.0) {
            viewController.showOptions();
        }

        return true;
    }
}
