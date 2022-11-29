package com.example.hangit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangit.databinding.ActivityYouWinBinding

class YouWinActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYouWinBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityYouWinBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        //Go to back the main screen
        binding.goBackYouWinButton.setOnClickListener {
            val intent = Intent(this@YouWinActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

        //Play Again
        binding.replayYouWinButton.setOnClickListener {
            val intent = Intent(this@YouWinActivity, GameActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}