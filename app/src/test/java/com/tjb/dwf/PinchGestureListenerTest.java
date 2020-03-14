package com.tjb.dwf;

import android.view.ScaleGestureDetector;

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
    private ViewController viewController;
    @Mock
    private ScaleGestureDetector detector;

    private PinchGestureListener pinchGestureListener;

    @Before
    public void setup() {
        pinchGestureListener = new PinchGestureListener(viewController);
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
        verifyZeroInteractions(viewController);
    }

    @Test
    public void onScale_thenPictureShown_whenScaleIsGreaterThan1() {
        // when
        when(detector.getScaleFactor()).thenReturn((float)1.1);

        // then
        boolean observed = pinchGestureListener.onScale(detector);

        // verify
        boolean expected = true;
        assertEquals(expected, observed);
        verify(viewController).showPicture();
    }

    @Test
    public void onScale_thenOptionsShown_whenScaleIsLessThan1() {
        // when
        when(detector.getScaleFactor()).thenReturn((float).9);

        // then
        boolean observed = pinchGestureListener.onScale(detector);

        // verify
        boolean expected = true;
        assertEquals(expected, observed);
        verify(viewController).showOptions();
    }
}
