package com.tess.features.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.tess.features.login.LoginActivity
import kotlinx.coroutines.delay

internal class SplashActivity : AppCompatActivity() {

    init {
        lifecycleScope.launchWhenResumed {
            delay(2000)
            navigateToLogin()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    private fun navigateToLogin(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}