package com.tjb.dwf.modules;

import android.content.Context;
import android.view.ScaleGestureDetector;

import com.tjb.dwf.DWFApplication;
import com.tjb.dwf.MainActivity;
import com.tjb.dwf.PinchGestureListener;
import com.tjb.dwf.PinchGestureReceiver;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@Module
@InstallIn(ActivityComponent.class)
public class PinchProviders {
    private final PinchGestureReceiver pinchGestureReceiver;
    private final PinchGestureListener pinchGestureListener;
    private final ScaleGestureDetector scaleGestureDetector;

    public PinchProviders() {
        pinchGestureReceiver = new PinchGestureReceiver();
        pinchGestureListener = new PinchGestureListener(pinchGestureReceiver);

        // TODO investigate timeline of this module getting constructed and the DWFApplication's onCreate
        scaleGestureDetector = new ScaleGestureDetector(DWFApplication.getAppContext(), pinchGestureListener);
    }

    @Provides
    public PinchGestureReceiver providePinchGestureReceiver() {
        return pinchGestureReceiver;
    }

    @Provides
    public PinchGestureListener providePinchGestureListener() {
        return pinchGestureListener;
    }

    @Provides
    public ScaleGestureDetector provideScaleGestureDetector() {
        return scaleGestureDetector;
    }
}
