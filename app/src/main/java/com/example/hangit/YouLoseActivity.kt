package com.example.hangit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangit.databinding.ActivityYouLoseBinding

class YouLoseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYouLoseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityYouLoseBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        //Go to back the main screen
        binding.goBackYouLostButton.setOnClickListener {
            val intent = Intent(this@YouLoseActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

        //Play Again
        binding.replayYouLoseButton.setOnClickListener {
            val intent = Intent(this@YouLoseActivity, GameActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}