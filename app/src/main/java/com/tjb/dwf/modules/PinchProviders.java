package com.tjb.dwf.modules;

import android.util.Log;

import com.tjb.dwf.MainActivity;
import com.tjb.dwf.PinchGestureListener;
import com.tjb.dwf.PinchGestureReceiver;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;

@Module
@InstallIn(MainActivity.class)
public class PinchProviders {
    private final PinchGestureReceiver pinchGestureReceiver;
    private final PinchGestureListener pinchGestureListener;

    public PinchProviders() {
        pinchGestureReceiver = new PinchGestureReceiver();
        pinchGestureListener = new PinchGestureListener(pinchGestureReceiver);
    }

    @Provides
    public PinchGestureReceiver providePinchGestureReceiver() {
        Log.e("PinchProviders", "providing receiver");
        return pinchGestureReceiver;
    }

    @Provides
    public PinchGestureListener providePinchGestureListener() {
        Log.e("PinchProviders", "providing listener");
        return pinchGestureListener;
    }
}
