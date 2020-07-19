package com.tjb.dwf;

import com.tjb.dwf.main.MainActivity;
import com.tjb.dwf.main.PinchGestureReceiver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PinchGestureReceiverTest {
    @Mock
    private MainActivity mainActivity;

    private PinchGestureReceiver pinchGestureReceiver;

    @Before
    public void setup() {
        pinchGestureReceiver = new PinchGestureReceiver();
        pinchGestureReceiver.installMainActivity(mainActivity);
    }

    @Test
    public void showOptions_then_showOptions() {
        // when
        pinchGestureReceiver.showOptions();

        // then
        // ...

        // verify
        verify(mainActivity).showOptions();
    }

    @Test
    public void showPicture_then_showPicture() {
        // when
        pinchGestureReceiver.showPicture();

        // then
        // ...

        // verify
        verify(mainActivity).showPicture();
    }
}
