package com.tjb.dwf.main;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.tjb.dwf.*

import javax.inject.Inject;

// steps:
// 0) create user with 2nd tutorial - done
// 1) login with 2nd tutorial from today - done
// 2) do that at the start - done
// 3) store and reuse jwt in single session - done
    // 3.5) consolidate user maintenance to a base activity class' onResume - done
// 4) store and reuse jwt in multiple sessions (not encrypted) - done
// 5) logout - done
// 5.5) di
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
class MainActivity : AuthenticatedActivity() {

    lateinit var mainComponent: MainComponent

    @Inject
    lateinit var pinchGestureReceiver: PinchGestureReceiver
    @Inject
    lateinit var scaleGestureDetector: ScaleGestureDetector

    // TODO var or val?
    private var pictureLayout: ConstraintLayout? = null
    private var optionsLayout: ConstraintLayout? = null

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {

        mainComponent = (application as DWFApplication).appComponent.mainComponent().create()
        mainComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pinchGestureReceiver.installMainActivity(this)

        pictureLayout = findViewById(R.id.pictureLayout)
        optionsLayout = findViewById(R.id.optionsLayout)

        showPicture()

        // Instantiate the cache
        val cache: Cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        val network: Network = BasicNetwork(HurlStack())
        RequestQueueSingleton.init(cache, network)
    }

    override fun onDestroy() {
        super.onDestroy()
        pinchGestureReceiver.uninstallMainActivity()
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val isPinch: Boolean = scaleGestureDetector.onTouchEvent(ev)

        return if (isPinch) true else super.onTouchEvent(ev)
    }

    fun onClickCreatePicture(v: View) {
        val intent: Intent = Intent(this, CreatePictureActivity::class.java)
        intent.putExtra(UserPojo.SERIALIZE_TAG, mUser)
        startActivity(intent)
    }

     fun onClickLogout(v: View) {
        mUser = null
        val sharedPref: SharedPreferences = getSharedPreferences("USER", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString("USER", null);
        editor.commit();

        val intent: Intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun optionsShowing(): Boolean {
        return pictureLayout!!.indexOfChild(optionsLayout) >= 2
    }

    fun showPicture() {
        if (optionsShowing()) {
            pictureLayout?.removeView(optionsLayout);
        }
    }

    fun showOptions() {
        if (!optionsShowing()) {
            pictureLayout?.addView(optionsLayout);
            pictureLayout?.bringChildToFront(optionsLayout);
        }
    }
}
