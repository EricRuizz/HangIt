package com.example.hangit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hangit.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        //If user already logged, it remembers it
        if (firebaseAuth.currentUser != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

        //Login when button is clicked
        binding.loginButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            if (username == "" || password == "") {

                //If the text is empty
                Toast.makeText(
                    this,
                    getString(R.string.empty_paramenters),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //Can login
                firebaseAuth.signInWithEmailAndPassword(username, password).addOnSuccessListener {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)

                    finish()

                }.addOnFailureListener {
                    //Can't login
                    Toast.makeText(
                        this,
                        getString(R.string.error_connection) + " " + it.message.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

        }

        //Guest login
        binding.loginAsGuestButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }

        //Go to register screen
        binding.buttongoToRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)

            finish()
        }

    }
}