package com.tjb.dwf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
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

import java.io.Serializable;

// steps:
// 0) create user with 2nd tutorial - done
// 1) login with 2nd tutorial from today - done
// 2) do that at the start - done
// 3) store and reuse jwt in single session
// 4) store and reuse jwt in multiple sessions (not encrypted)
// 5) store and reuse jwt in multiple sessions, encrypted
// 6) research token expiration
// 7) validate input
// 8) test
public class MainActivity extends AppCompatActivity {

    private UserPojo mUser;

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

        // check login status
        Serializable result = getIntent().getSerializableExtra(UserPojo.SERIALIZE_TAG);
        mUser = result instanceof UserPojo ? (UserPojo) result : null;
        if (mUser == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            // TODO personalize interface
            Log.e("MainActivity", "firstname is " + mUser.firstName + " and lastname is " + mUser.lastName);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean isPinch = scaleGestureDetector.onTouchEvent(ev);

        return isPinch ? true : super.onTouchEvent(ev);
    }

    public void onClickCreatePicture(View v) {
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
