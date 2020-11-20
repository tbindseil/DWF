package com.tjb.dwf.main;

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.tjb.dwf.*
import com.tjb.dwf.user.UserController
import com.tjb.dwf.utils.NewActivityIntentFactory
import javax.inject.Inject

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

        val optionsText = findViewById<TextView>(R.id.optionsText)
        optionsText.setText("Hello " + userController.getUser()?.username)

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
