package com.tjb.dwf.main;

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Cache
import com.android.volley.Network
import com.android.volley.toolbox.BasicNetwork
import com.android.volley.toolbox.DiskBasedCache
import com.android.volley.toolbox.HurlStack
import com.tjb.dwf.*
import com.tjb.dwf.user.UserController
import com.tjb.dwf.webclient.RequestQueueSingleton
import javax.inject.Inject

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
class MainActivity : AppCompatActivity() {

    lateinit var mainSubcomponent: MainSubcomponent

    @Inject
    lateinit var userController: UserController
    @Inject
    lateinit var pinchGestureListener: PinchGestureListener
    @Inject
    lateinit var scaleGestureDetector: ScaleGestureDetectorWrapper

    // TODO var or val? maybe lateinit val?
    private var pictureLayout: ConstraintLayout? = null
    private var optionsLayout: ConstraintLayout? = null

    @Override
    override fun onCreate(savedInstanceState: Bundle?) {

        mainSubcomponent = (application as DWFApplication).appComponent.mainComponent().create()
        mainSubcomponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pinchGestureListener.installMainActivity(this)

        pictureLayout = findViewById(R.id.pictureLayout)
        optionsLayout = findViewById(R.id.optionsLayout)

        showPicture()

        // Instantiate the cache
        val cache: Cache = DiskBasedCache(cacheDir, 1024 * 1024) // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        val network: Network = BasicNetwork(HurlStack())
        RequestQueueSingleton.init(cache, network)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val isPinch: Boolean = scaleGestureDetector.onTouchEvent(ev)

        return if (isPinch) true else super.onTouchEvent(ev)
    }

    fun onClickCreatePicture() {
        val intent: Intent = Intent(this, CreatePictureActivity::class.java)
        startActivity(intent)
    }

    fun onClickLogout() {
        userController.logout(this)
    }

    private fun optionsShowing(): Boolean {
        return pictureLayout!!.indexOfChild(optionsLayout) >= 0
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
