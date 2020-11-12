package com.tjb.dwf.main;

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.tjb.dwf.*
import com.tjb.dwf.user.UserController
import com.tjb.dwf.utils.NewActivityIntentFactory
import javax.inject.Inject

// steps:
// 0) create user with 2nd tutorial - done
// 1) login with 2nd tutorial from today - done
// 2) do that at the start - done
// 3) store and reuse jwt in single session - done
    // 3.5) consolidate user maintenance to a base activity class' onResume - done
// 4) store and reuse jwt in multiple sessions (not encrypted) - done
// 5) logout - done
// 5.5) di - done
// 6) test
    // activities redirect to LoginActivity if not LoginActivity - scratched
    // splash screen activity goes to login/main whether or not user logged in - done
    // LoginActivity
        // username and password are checked on submission
        // upon bad login, a message is shown
        // upon good login, mainActivity is started
    // MainActivity
        // starts in pictureView
        // pinch ends in optionsView - scratch
        // open pinch ends in pictureView - scratch
    // CreatePictureActivity - probably doesn't need to be tested
// 5) store and reuse jwt in multiple sessions, encrypted - scratched
// 6) research token expiration - scratch
// 7) validate input
// 8) test
class MainActivity : AppCompatActivity() {

    lateinit var mainSubcomponent: MainSubcomponent

    @Inject
    lateinit var userController: UserController
    @Inject
    lateinit var pinchGestureListener: PinchGestureListener
    @Inject
    lateinit var scaleGestureDetector: ScaleGestureDetector
    @Inject
    lateinit var newActivityIntentFactory: NewActivityIntentFactory

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
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        val isPinch: Boolean = scaleGestureDetector.onTouchEvent(ev)

        return if (isPinch) true else super.onTouchEvent(ev)
    }

    fun onClickCreatePicture(v: View) {
        val intent = newActivityIntentFactory.makeNewActivityIntent(this, CreatePictureActivity::class.java)
        startActivity(intent)
    }

    fun onClickLogout(v: View) {
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
