package com.example.hangit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hangit.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        //Go to game screen
        binding.playButton.setOnClickListener {
            val intent = Intent(this@MainActivity, GameActivity::class.java)
            startActivity(intent)

            finish()
        }

        //Go to ranking screen
        binding.rankingButton.setOnClickListener {
            val intent = Intent(this@MainActivity, RankingActivity::class.java)
            startActivity(intent)

            finish()
        }

        //Go to user screen
        binding.userButton.setOnClickListener {
            val intent = Intent(this@MainActivity, UserActivity::class.java)
            startActivity(intent)

            finish()
        }

        //Go to settings screen
        binding.settingsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)

            finish()
        }

        //Go to ad screen
        binding.adButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AdActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}