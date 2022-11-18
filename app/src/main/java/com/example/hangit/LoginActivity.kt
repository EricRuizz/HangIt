package com.example.hangit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.hangit.databinding.ActivityLoginBinding
import com.google.firebase.auth.FederatedAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.OAuthProvider
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        //Login when button is clicked
        binding.loginButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()

            //Can login
            firebaseAuth.signInWithEmailAndPassword(username, password).addOnSuccessListener {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intent)

                finish()

            }.addOnFailureListener {
                //Can't login
                Toast.makeText(this, getString(R.string.error_password), Toast.LENGTH_SHORT).show()
            }

        }

        //Go to register screen
        binding.buttongoToRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)

                finish()
        }

    }
}