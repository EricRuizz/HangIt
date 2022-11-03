package com.example.hangit

import com.example.hangit.databinding.ActivitySplashScreenBinding

class SplashScreenActivity {
    private lateinit var binding : ActivitySplashScreenBinding
    binding = ActivitySplashScreenBinding.inflate(layoutInflater)
    setContentView(binding.root)
}