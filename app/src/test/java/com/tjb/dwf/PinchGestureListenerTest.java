package com.tjb.dwf;

import android.view.ScaleGestureDetector;

import com.tjb.dwf.main.PinchGestureListener;
import com.tjb.dwf.main.PinchGestureReceiver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PinchGestureListenerTest {
    @Mock
    private PinchGestureReceiver pinchGestureReceiver;
    @Mock
    private ScaleGestureDetector detector;

    private PinchGestureListener pinchGestureListener;

    @Before
    public void setup() {
        pinchGestureListener = new PinchGestureListener(pinchGestureReceiver);
    }

    @Test
    public void onScale_thenTrue_always() {
        // when
        when(detector.getScaleFactor()).thenReturn((float)1.0);

        // then
        boolean observed = pinchGestureListener.onScale(detector);

        // verify
        boolean expected = true;
        assertEquals(expected, observed);
        verifyZeroInteractions(pinchGestureReceiver);
    }

    @Test
    public void onScale_thenPictureShown_whenScaleIsGreaterThanOneTenTimesStraight() {
        // when
        for (int i = 0; i < 10; ++i) {
            when(detector.getScaleFactor()).thenReturn((float)1.1);
        }

        // then
        boolean observed = false;
        for (int i = 0; i < 10; ++i) {
            observed = pinchGestureListener.onScale(detector);
        }

        // verify
        boolean expected = true;
        assertEquals(expected, observed);
        verify(pinchGestureReceiver).showPicture();
    }

    @Test
    public void onScale_thenOptionsShown_whenScaleIsLessThanOneTenTimesStraight() {
        // when
        for (int i = 0; i < 10; ++i) {
            when(detector.getScaleFactor()).thenReturn((float).9);
        }

        // then
        boolean observed = false;
        for (int i = 0; i < 10; ++i) {
            observed = pinchGestureListener.onScale(detector);
        }

        // verify
        boolean expected = true;
        assertEquals(expected, observed);
        verify(pinchGestureReceiver).showOptions();
    }

    @Test
    public void onScale_thenNothing_whenScaleIsGreaterThanOneNineTimes_afterLessThanOneOnce() {
        // when
        when(detector.getScaleFactor()).thenReturn((float).9);
        for (int i = 0; i < 9; ++i) {
            when(detector.getScaleFactor()).thenReturn((float)1.1);
        }

        // then
        boolean observed = false;
        for (int i = 0; i < 9; ++i) {
            observed = pinchGestureListener.onScale(detector);
        }

        // verify
        boolean expected = true;
        assertEquals(expected, observed);
        verifyZeroInteractions(pinchGestureReceiver);
    }

    @Test
    public void onScale_thenNothing_whenScaleIsLessThanOneNineTimes_afterGreaterThanOneOnce() {
        // when
        when(detector.getScaleFactor()).thenReturn((float)1.1);
        for (int i = 0; i < 9; ++i) {
            when(detector.getScaleFactor()).thenReturn((float).9);
        }

        // then
        boolean observed = false;
        for (int i = 0; i < 9; ++i) {
            observed = pinchGestureListener.onScale(detector);
        }

        // verify
        boolean expected = true;
        assertEquals(expected, observed);
        verifyZeroInteractions(pinchGestureReceiver);
    }
}
