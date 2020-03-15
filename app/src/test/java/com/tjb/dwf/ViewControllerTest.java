package com.tjb.dwf;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ViewControllerTest {
    @Mock
    private MainActivity mainActivity;

    private ViewController viewController;

    @Before
    public void setup() {
        viewController = new ViewController(mainActivity);
    }

    @Test
    public void showPicture_callsMainActivityShowPicture_always() {
        // when

        // then
        viewController.showPicture();

        // verify
        verify(mainActivity).showPicture();
    }

    @Test
    public void showOptions_callsMainActivityShowOptions_always() {
        // when

        // then
        viewController.showOptions();

        // verify
        verify(mainActivity).showOptions();
    }
}
