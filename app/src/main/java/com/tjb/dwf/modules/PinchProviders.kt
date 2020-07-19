package com.tjb.dwf.modules;

import android.content.Context;
import android.view.ScaleGestureDetector;

import com.tjb.dwf.main.PinchGestureListener;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
class PinchProviders @Inject constructor(private val context: Context, private val pinchGestureListener: PinchGestureListener) {

    @Provides
    fun provideScaleGestureDetector(): ScaleGestureDetector {
        return ScaleGestureDetector(context, pinchGestureListener);
    }
}
