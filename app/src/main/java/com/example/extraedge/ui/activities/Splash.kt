package com.example.extraedge.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.extraedge.databinding.ActivitySplashBinding

class Splash : AppCompatActivity()
{
    private var binding : ActivitySplashBinding? = null
    private var splashDelay: Int = 4000
    private var navigating: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(getLayoutInflater())
        val view = binding?.root
        setContentView(view)
        initViews()
    }

    private fun initViews() {
        // Appying fade in animation
        Handler().postDelayed(
            { gotoNextScreen() },
            splashDelay.toLong()
        )
    }

    private fun gotoNextScreen()
    {
        navigating = true
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

}