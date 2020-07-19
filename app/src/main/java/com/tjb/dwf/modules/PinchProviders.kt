package com.tjb.dwf.modules;

import android.content.Context;
import android.view.ScaleGestureDetector;
import com.tjb.dwf.di.ActivityScope

import com.tjb.dwf.main.PinchGestureListener;
import dagger.Binds

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
abstract class PinchProviders  {
    @Binds
    @ActivityScope
    abstract fun bindScaleGestureDetector(scaleGestureDetector: ScaleGestureDetector): ScaleGestureDetector
}
