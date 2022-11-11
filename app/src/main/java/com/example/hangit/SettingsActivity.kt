package com.example.hangit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangit.databinding.ActivityLoginBinding
import com.example.hangit.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}