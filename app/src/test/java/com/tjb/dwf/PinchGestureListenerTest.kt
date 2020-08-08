package com.tjb.dwf

import android.view.ScaleGestureDetector
import com.tjb.dwf.main.MainActivity
import com.tjb.dwf.main.PinchGestureListener
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PinchGestureListenerTest {
    private val mainActivity = mockk<MainActivity>(relaxed = true)
    private val scaleGestureDetector = mockk<ScaleGestureDetector>()

    private val pinchGestureListener = PinchGestureListener()

    @Before
    fun setup() {
        pinchGestureListener.installMainActivity(mainActivity)
    }

    @Test
    fun onScale_thenTrue_always() {
        // when
        every { scaleGestureDetector.scaleFactor } returns 1.0f

        // then
        val observed = pinchGestureListener.onScale(scaleGestureDetector);

        // verify
        val expected = true;
        assertEquals(expected, observed);
    }

    @Test
    fun onScale_whenScaleFactor1_thenHistoryRemains() {
        // when
        every { scaleGestureDetector.scaleFactor } returns 1.1f
        for (i in 0 until 5) {
            pinchGestureListener.onScale(scaleGestureDetector)
        }

        // and
        every { scaleGestureDetector.scaleFactor } returns 1.0f
        pinchGestureListener.onScale(scaleGestureDetector)

        // then
        every { scaleGestureDetector.scaleFactor } returns 1.1f
        var observed = false
        for (i in 0 until 5) {
            observed = pinchGestureListener.onScale(scaleGestureDetector)
        }

        // verify
        val expected = true;
        assertEquals(expected, observed);
        verify(exactly = 1) {
            // continue here... not quite sure if i need to go further, but it did feel right to
            // eliminate pinchGestureReceiver as it was an unneccessay middle man. How can i avoid
            // the install"" business? TODO
            //
            // maybe a cause for the view controller. then when other user input needs to interact
            // it will go through the view controller
            // we'll see
            mainActivity.showPicture()
        }
    }

    @Test
    fun onScale_thenPictureShown_whenScaleIsGreaterThanOneTenTimesStraight() {
        // when
        every { scaleGestureDetector.scaleFactor } returns 1.1f

        // then
        var observed = false;
        for (i in 0 until 10) {
            observed = pinchGestureListener.onScale(scaleGestureDetector);
        }

        // verify
        val expected = true;
        assertEquals(expected, observed);
        verify(exactly = 1) {
            mainActivity.showPicture()
        }
    }

    @Test
    fun onScale_thenOptionsShown_whenScaleIsLessThanOneTenTimesStraight() {
        // when
        every { scaleGestureDetector.scaleFactor } returns .9f

        // then
        var observed = false
        for (i in 0 until 10) {
            observed = pinchGestureListener.onScale(scaleGestureDetector)
        }

        // verify
        val expected = true;
        assertEquals(expected, observed);
        verify(exactly = 1) {
            mainActivity.showOptions()
        }
    }

    @Test
    fun onScale_thenNothing_whenScaleIsGreaterThanOneNineTimes_afterLessThanOneOnce() {
        // when
        every { scaleGestureDetector.scaleFactor } returns .9f

        // then
        var observed = false;
        observed = pinchGestureListener.onScale(scaleGestureDetector);

        // when
        every { scaleGestureDetector.scaleFactor } returns 1.1f

        // then
        for (i in 0 until 9) {
            observed = pinchGestureListener.onScale(scaleGestureDetector);
        }

        // verify
        val expected = true;
        assertEquals(expected, observed);
        verify {
            mainActivity wasNot Called
        }
    }

    @Test
    fun onScale_thenNothing_whenScaleIsLessThanOneNineTimes_afterGreaterThanOneOnce() {
        // when
        every { scaleGestureDetector.scaleFactor } returns 1.1f

        // then
        var observed = false;
        observed = pinchGestureListener.onScale(scaleGestureDetector);

        // when
        every { scaleGestureDetector.scaleFactor } returns .9f

        // then
        for (i in 0 until 9) {
            observed = pinchGestureListener.onScale(scaleGestureDetector);
        }

        // verify
        val expected = true;
        assertEquals(expected, observed);
        verify {
            mainActivity wasNot Called
        }
    }
}
