package com.example.hangit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.hangit.databinding.ActivityAdBinding

class AdActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityAdBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Go to back the main screen
        binding.goBackButtonAD.setOnClickListener {
            val intent = Intent(this@AdActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

    }
}