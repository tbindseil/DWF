package com.tjb.dwf.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tjb.dwf.DWFApplication
import com.tjb.dwf.R
import com.tjb.dwf.main.MainActivity
import javax.inject.Inject

class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {

        (application as DWFApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // maybe get user in its own thread?
        var nextActivity =
                if (userController.getUser(this) == null)
                    LoginActivity::class.java
                else
                    MainActivity::class.java


        val intent = Intent(this, nextActivity)
        this.startActivity(intent)
        finish()
    }
}