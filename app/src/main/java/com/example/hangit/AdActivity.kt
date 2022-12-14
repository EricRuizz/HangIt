package com.example.hangit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangit.databinding.ActivityAdBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds

class AdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityAdBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        //Play Ad
        MobileAds.initialize(this@AdActivity)
        val request = AdRequest.Builder().build()
        binding.adView.loadAd(request)
        binding.adView2.loadAd(request)

        //Go to back the main screen
        binding.goBackButtonAD.setOnClickListener {
            val intent = Intent(this@AdActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

    }
}