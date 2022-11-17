package com.example.hangit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hangit.databinding.ActivityLoginBinding
import com.example.hangit.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val password2 = binding.passwordInput.text.toString()

            //TODO: mirar si l usuari existeix
                if (password2 == password2) {
                    firebaseAuth.createUserWithEmailAndPassword(username, password)
                        .addOnSuccessListener {
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            startActivity(intent)

                            finish()

                        }.addOnFailureListener {

                            Toast.makeText(
                                this,
                                getString(R.string.error_password),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                } else {
                    Toast.makeText(this, getString(R.string.register_error), Toast.LENGTH_SHORT)
                        .show()
                }

        }
        binding.buttongoToLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)

            finish()
        }
        }
    }
