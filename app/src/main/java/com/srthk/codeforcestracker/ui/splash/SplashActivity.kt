package com.srthk.codeforcestracker.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.srthk.codeforcestracker.ui.MainActivity
import com.srthk.codeforcestracker.R.layout
import com.srthk.codeforcestracker.util.Constants.SPLASH_TIME

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_splash)
        Handler().postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME)
    }
}
