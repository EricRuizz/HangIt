package com.example.hangit

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.hangit.databinding.ActivitySettingsBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private var audioOn: Boolean = true
    private var notificationOn: Boolean = true

    companion object {
        const val CHANNEL_ID = "NOTIFICATIONS_CHANNEL_CONFIG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Create channel notifications
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    "PublicityNotifications",
                    NotificationManager.IMPORTANCE_LOW
                )

            val manager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

        firebaseAuth = FirebaseAuth.getInstance()

        firestore = FirebaseFirestore.getInstance()
        FirebaseFirestore.setLoggingEnabled(true)

        val collection = firestore.collection("userConfiguration")

        val shared = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = shared.edit()

        if (firebaseAuth.currentUser != null) {
            collection.document(firebaseAuth.currentUser?.email.toString()).get()
                .addOnCompleteListener(object : OnCompleteListener<DocumentSnapshot> {
                    override fun onComplete(task: Task<DocumentSnapshot>) {
                        if (task.isSuccessful()) {
                            val document = task.getResult()
                            if (document.exists()) {
                            } else {
                                collection.document(firebaseAuth.currentUser?.email.toString())
                                    .set(notificationOn to "notificationOn")

                                collection.document(firebaseAuth.currentUser?.email.toString())
                                    .set(audioOn to "audioOn")
                            }
                        }
                    }
                })

            collection.document(firebaseAuth.currentUser?.email.toString()).get()
                .addOnSuccessListener {
                    audioOn = it.getBoolean("audioOn") ?: true
                    notificationOn = it.getBoolean("notificationOn") ?: true

                    if (firebaseAuth.currentUser == null) {
                        audioOn = shared.getBoolean("audioOn", audioOn)
                        notificationOn = shared.getBoolean("notificationOn", notificationOn)
                    }

                    applyLoadedSettings()

                }.addOnFailureListener {
                    audioOn = shared.getBoolean("audioOn", audioOn)
                    notificationOn = shared.getBoolean("notificationOn", notificationOn)

                    Toast.makeText(
                        this,
                        getString(R.string.error_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }
        } else {
            audioOn = shared.getBoolean("audioOn", audioOn)
            notificationOn = shared.getBoolean("notificationOn", notificationOn)

            applyLoadedSettings()
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

            editor.putBoolean("audioOn", audioOn)
            editor.apply()
        }

        //Activate or Deactivate notifications
        binding.notificationButton.setOnClickListener {

            if (!notificationOn) {
                notificationOn = true
                binding.notificationsTick.setVisibility(View.VISIBLE)

                //Activate notifications
                //Posar timer que cada x temps surti noti i posarho a una activitat de service
                val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.gametire)
                    .setContentTitle("Hang It!")
                    .setContentText("Come to play! We miss you!").build()

                with(NotificationManagerCompat.from(this)) {
                    notify(System.currentTimeMillis().toInt(), builder)
                }

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

            editor.putBoolean("notificationOn", notificationOn)
            editor.apply()
        }
    }

    fun applyLoadedSettings() {
        //Update audio
        if (audioOn) {
            binding.audioTick.setVisibility(View.VISIBLE)
            //set audio

        } else {
            binding.audioTick.setVisibility(View.GONE)
            //deactivate audio
        }

        //Update notifications
        if (notificationOn) {
            binding.notificationsTick.setVisibility(View.VISIBLE)
            //set notification

        } else {
            binding.notificationsTick.setVisibility(View.GONE)
            //deactivate notification
        }
    }
}