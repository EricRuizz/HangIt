package com.example.hangit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hangit.databinding.ActivityRankingBinding
import com.example.hangit.databinding.ActivityRegisterBinding

class RankingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRankingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}