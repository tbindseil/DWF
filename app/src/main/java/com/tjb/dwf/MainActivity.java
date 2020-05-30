package com.tjb.dwf;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

// steps:
// 0) create user with 2nd tutorial - done
// 1) login with 2nd tutorial from today - done
// 2) do that at the start - done
// 3) store and reuse jwt in single session - done
    // 3.5) consolidate user maintenance to a base activity class' onResume - done
// 4) store and reuse jwt in multiple sessions (not encrypted) - done
// 5) logout - done
// 6) test
    // activities redirect to LoginActivity if not LoginActivity
    // LoginActivity
        // username and password are checked on submission
        // upon bad login, a message is shown
        // upon good login, mainActivity is started
    // MainActivity
        // starts in pictureView
        // pinch ends in optionsView
        // open pinch ends in pictureView
    // CreatePictureActivity - probably doesn't need to be tested
// 5) store and reuse jwt in multiple sessions, encrypted
// 6) research token expiration
// 7) validate input
// 8) test
public class MainActivity extends AuthenticatedActivity {

    private ViewController viewController;
    private ScaleGestureDetector scaleGestureDetector;

    private ConstraintLayout pictureLayout;
    private ConstraintLayout optionsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pictureLayout = findViewById(R.id.pictureLayout);
        optionsLayout = findViewById(R.id.optionsLayout);

        showPicture();

        viewController = new ViewController(this);
        PinchGestureListener pinchGestureListener = new PinchGestureListener(viewController);
        scaleGestureDetector = new ScaleGestureDetector(this, pinchGestureListener);

        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        RequestQueueSingleton.init(cache, network);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean isPinch = scaleGestureDetector.onTouchEvent(ev);

        return isPinch ? true : super.onTouchEvent(ev);
    }

    public void onClickCreatePicture(View v) {
        Intent intent = new Intent(this, CreatePictureActivity.class);
        intent.putExtra(UserPojo.SERIALIZE_TAG, mUser);
        startActivity(intent);
    }

    public void onClickLogout(View v) {
        mUser = null;
        SharedPreferences sharedPref = getSharedPreferences("USER", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("USER", null);
        editor.commit();

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private boolean optionsShowing() {
        return pictureLayout.indexOfChild(optionsLayout) >= 0;
    }

    public void showPicture() {
        if (optionsShowing()) {
            pictureLayout.removeView(optionsLayout);
        }
    }

    public void showOptions() {
        if (!optionsShowing()) {
            pictureLayout.addView(optionsLayout);
            pictureLayout.bringChildToFront(optionsLayout);
        }
    }
}
