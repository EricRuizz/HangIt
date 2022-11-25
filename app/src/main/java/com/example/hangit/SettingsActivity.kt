package com.example.hangit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hangit.databinding.ActivityLoginBinding
import com.example.hangit.databinding.ActivitySettingsBinding
import com.google.firebase.auth.FirebaseAuth

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        //Logout and go to login screen
        binding.logOutButton.setOnClickListener {
            if (firebaseAuth.currentUser != null) {
                firebaseAuth.signOut()
                val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
                startActivity(intent)

                finish()
            } else {
                //Can't logout, user is a guest
                Toast.makeText(
                    this,
                    getString(R.string.error_logOut),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //Go to back the main screen
        binding.goBackButtonSettings.setOnClickListener {
            val intent = Intent(this@SettingsActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }


}