package com.tjb.dwf

import com.tjb.dwf.main.MainActivity
import com.tjb.dwf.main.PinchGestureReceiver
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.apache.tools.ant.Main
import org.junit.Before
import org.junit.Test

class PinchGestureReceiverTest {
    private val pinchGestureReceiver = PinchGestureReceiver()

    private val mainActivity = mockk<MainActivity>(relaxed = true)

    @Before
    fun setup() {
        pinchGestureReceiver.installMainActivity(mainActivity);
    }

    @Test
    fun showOptions_then_showOptions() {
        // when
        pinchGestureReceiver.showOptions();

        // then
        // ...

        // verify
        verify {
            mainActivity.showOptions()
        }
    }

    @Test
    fun showPicture_then_showPicture() {
        // when
        pinchGestureReceiver.showPicture()

        // then
        // ...

        // verify
        verify {
            mainActivity.showPicture()
        }
    }
}
