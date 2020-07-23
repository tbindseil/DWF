package com.tjb.dwf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tjb.dwf.main.MainActivity
import com.tjb.dwf.user.UserController
import javax.inject.Inject

class SplashScreenActivity : AppCompatActivity() {

    @Inject
    lateinit var userController: UserController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        // maybe get user in its own thread?
        var nextActivity =
                if (userController.getUser(this) == null)
                    LoginActivity::class.java
                else
                    MainActivity::class.java
    }
}