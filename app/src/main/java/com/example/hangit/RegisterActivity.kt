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

        supportActionBar?.hide()

        firebaseAuth = FirebaseAuth.getInstance()

        //Register button is clicked
        binding.registerButton.setOnClickListener {
            val username = binding.userInput.text.toString()
            val password = binding.passwordInput.text.toString()
            val password2 = binding.registerPasswordInput.text.toString()

            if (username == "" || password == "" || password2 == "") {

                //If the text is empty
                Toast.makeText(
                    this,
                    getString(R.string.empty_paramenters),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //If password 1 is the same that password 2, user can be registred
                if (password == password2) {
                    firebaseAuth.createUserWithEmailAndPassword(username, password)
                        .addOnSuccessListener {
                            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                            startActivity(intent)

                            finish()

                        }.addOnFailureListener {
                            Toast.makeText(
                                this,
                                getString(R.string.error_connection) + " " + it.message.toString(),
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }

                } else {
                    Toast.makeText(this, getString(R.string.register_error), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        //If the user has already an account, a login button to go to the login screen
        binding.buttongoToLogin.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)

            finish()
        }
    }
}
