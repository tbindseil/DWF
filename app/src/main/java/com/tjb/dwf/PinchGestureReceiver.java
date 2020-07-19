package com.tjb.dwf;

import android.util.Log;

public class PinchGestureReceiver {
    private MainActivity mainActivity;

    public void installMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void detachMainActivity() {
        this.mainActivity = null;
    }

    public void showOptions() {
        if (mainActivity != null) {
            mainActivity.showOptions();
        } else {
            Log.e("PinchGestureReceiver", "MainActivity is null");
        }
    }

    public void showPicture() {
        if (mainActivity != null) {
            mainActivity.showPicture();
        } else {
            Log.e("PinchGestureReceiver", "MainActivity is null");
        }
    }
}
