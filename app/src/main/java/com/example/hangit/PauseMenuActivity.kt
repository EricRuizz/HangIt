package com.example.hangit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangit.databinding.ActivityPauseMenuBinding

class PauseMenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPauseMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPauseMenuBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}