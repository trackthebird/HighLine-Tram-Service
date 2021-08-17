package com.highline.tramservice

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.highline.tramservice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        // Show Splash Screen
        Handler(Looper.getMainLooper()).postDelayed({
            mBinding.idSplashScreen.visibility = View.GONE
            mBinding.idRlTramDetailsView.visibility = View.VISIBLE
        }, 3000)
    }
}