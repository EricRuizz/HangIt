package com.example.hangit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.hangit.databinding.ActivityLoginBinding
import com.example.hangit.databinding.ActivitySettingsBinding
import com.google.firebase.FirebaseAppLifecycleListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var audioOn: Boolean = true
    private var notificationOn: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()
        FirebaseFirestore.setLoggingEnabled(true)

        val collection = firestore.collection("userConfiguration")
        collection.document(firebaseAuth.currentUser?.email.toString()).get().addOnSuccessListener {
            audioOn = it.getBoolean("audioOn") ?: true
            if (audioOn) {
                binding.audioTick.setVisibility(View.VISIBLE)
                //set audio

            } else {
                binding.audioTick.setVisibility(View.GONE)
                //deactivate audio
            }

            notificationOn = it.getBoolean("notificationOn") ?: true
            if (notificationOn) {
                binding.notificationsTick.setVisibility(View.VISIBLE)
                //set notification

            } else {
                binding.notificationsTick.setVisibility(View.GONE)
                //deactivate notification
            }

        }.addOnFailureListener {
            Toast.makeText(
                this,
                getString(R.string.error_connection),
                Toast.LENGTH_SHORT
            ).show()
        }


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

        //Activate or Deactivate audio
        binding.audioButton.setOnClickListener {
            if (!audioOn) {
                audioOn = true
                binding.audioTick.setVisibility(View.VISIBLE)
                //Activate music


            } else {
                audioOn = false
                binding.audioTick.setVisibility(View.GONE)
                //Deactivate music
            }

            if (firebaseAuth.currentUser != null) {
                //Save Configuration
                val collection = firestore.collection("userConfiguration")
                collection.document(firebaseAuth.currentUser?.email.toString())
                    .update("audioOn", audioOn)

            } else {
                //Can't save configuration, user is a guest
                Toast.makeText(
                    this,
                    getString(R.string.can_not_save),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //Activate or Deactivate notifications
        binding.notificationButton.setOnClickListener {
            if (!notificationOn) {
                notificationOn = true
                binding.notificationsTick.setVisibility(View.VISIBLE)
                //Activate notifications


            } else {
                notificationOn = false
                binding.notificationsTick.setVisibility(View.GONE)
                //Deactivate notifications
            }

            if (firebaseAuth.currentUser != null) {
                //Save Configuration
                val collection = firestore.collection("userConfiguration")
                collection.document(firebaseAuth.currentUser?.email.toString())
                    .update("notificationOn", notificationOn)

            } else {
                //Can't save configuration, user is a guest
                Toast.makeText(
                    this,
                    getString(R.string.can_not_save),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


}