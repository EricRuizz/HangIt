package com.example.hangit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.hangit.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val CORRECT_USERNAME = "Focalita"
    private val CORRECT_PASSWORD = "Focalita2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        binding.loginButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if(username == CORRECT_USERNAME && password == CORRECT_PASSWORD) {
                //hem fet login
                runBlocking {
                    CoroutineScope(Dispatchers.Default).launch {
                        binding.progressBar.visibility = View.VISIBLE
                        delay(3000)

                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)

                        finish()
                    }
                }

            } else {
                Toast.makeText(this, getString(R.string.error_password), Toast.LENGTH_SHORT).show()
            }
        }
        */
    }
}