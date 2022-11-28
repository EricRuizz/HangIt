package com.example.hangit


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.hangit.databinding.ActivityGameBinding
import com.example.hangit.hangman.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var gameInfo: ResponseCreateGame
    private lateinit var letterInfo: ResponseGuessLetter


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityGameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hangman-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        createGame(retrofit)

        //Player guessed letter A
        binding.letterA.setOnClickListener {
            guessLetter(retrofit, "A", binding.letterA)
        }

        //Player guessed letter B
        binding.letterB.setOnClickListener {
            guessLetter(retrofit, "B", binding.letterB)
        }

        //Player guessed letter C
        binding.letterC.setOnClickListener {
            guessLetter(retrofit, "C", binding.letterC)
        }

        //Player guessed letter D
        binding.letterD.setOnClickListener {
            guessLetter(retrofit, "D", binding.letterD)
        }

        //Player guessed letter E
        binding.letterE.setOnClickListener {
            guessLetter(retrofit, "E", binding.letterE)
        }

        //Player guessed letter F
        binding.letterF.setOnClickListener {
            guessLetter(retrofit, "F", binding.letterF)
        }

        //Player guessed letter G
        binding.letterG.setOnClickListener {
            guessLetter(retrofit, "G", binding.letterG)
        }

        //Player guessed letter H
        binding.letterH.setOnClickListener {
            guessLetter(retrofit, "H", binding.letterH)
        }

        //Player guessed letter I
        binding.letterI.setOnClickListener {
            guessLetter(retrofit, "I", binding.letterI)
        }

        //Player guessed letter J
        binding.letterJ.setOnClickListener {
            guessLetter(retrofit, "J", binding.letterJ)
        }

        //Player guessed letter K
        binding.letterK.setOnClickListener {
            guessLetter(retrofit, "K", binding.letterK)
        }

        //Player guessed letter L
        binding.letterL.setOnClickListener {
            guessLetter(retrofit, "L", binding.letterL)
        }

        //Player guessed letter M
        binding.letterM.setOnClickListener {
            guessLetter(retrofit, "M", binding.letterM)
        }

        //Player guessed letter N
        binding.letterN.setOnClickListener {
            guessLetter(retrofit, "N", binding.letterN)
        }

        //Player guessed letter O
        binding.letterO.setOnClickListener {
            guessLetter(retrofit, "O", binding.letterO)
        }

        //Player guessed letter P
        binding.letterP.setOnClickListener {
            guessLetter(retrofit, "P", binding.letterP)
        }

        //Player guessed letter Q
        binding.letterQ.setOnClickListener {
            guessLetter(retrofit, "Q", binding.letterQ)
        }

        //Player guessed letter R
        binding.letterR.setOnClickListener {
            guessLetter(retrofit, "R", binding.letterR)
        }

        //Player guessed letter S
        binding.letterS.setOnClickListener {
            guessLetter(retrofit, "S", binding.letterS)
        }

        //Player guessed letter T
        binding.letterT.setOnClickListener {
            guessLetter(retrofit, "T", binding.letterT)
        }

        //Player guessed letter U
        binding.letterU.setOnClickListener {
            guessLetter(retrofit, "U", binding.letterU)
        }

        //Player guessed letter V
        binding.letterV.setOnClickListener {
            guessLetter(retrofit, "V", binding.letterV)
        }

        //Player guessed letter W
        binding.letterW.setOnClickListener {
            guessLetter(retrofit, "W", binding.letterW)
        }

        //Player guessed letter X
        binding.letterX.setOnClickListener {
            guessLetter(retrofit, "X", binding.letterX)
        }

        //Player guessed letter Y
        binding.letterY.setOnClickListener {
            guessLetter(retrofit, "Y", binding.letterY)
        }

        //Player guessed letter Z
        binding.letterZ.setOnClickListener {
            guessLetter(retrofit, "Z", binding.letterZ)
        }

        //Player guessed letter SPACE
        binding.space.setOnClickListener {
            guessLetter(retrofit, " ", binding.space)
        }
    }


    //API Hangman
    fun createGame(retrofit: Retrofit) {

        val call = retrofit.create(ApiHangman::class.java)
        call.createGame().enqueue(object : Callback<ResponseCreateGame> {
            override fun onResponse(
                call: Call<ResponseCreateGame>,
                response: Response<ResponseCreateGame>
            ) {
                //Save the game info and check if null
                gameInfo = response.body() ?: ResponseCreateGame("", "")
            }

            override fun onFailure(call: Call<ResponseCreateGame>, t: Throwable) {
                Toast.makeText(
                    this@GameActivity,
                    getString(R.string.error_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun guessLetter(retrofit: Retrofit, letter: String, button: Button) {
        val call = retrofit.create(ApiHangman::class.java)
        call.guessLetter(gameInfo.token, letter)
            .enqueue(object : Callback<ResponseGuessLetter> {
                override fun onResponse(
                    call: Call<ResponseGuessLetter>,
                    response: Response<ResponseGuessLetter>
                ) {
                    //Save the game info and check if null
                    letterInfo = response.body() ?: ResponseGuessLetter("", "", false)

                    if (!button.isClickable) {
                        Toast.makeText(
                            this@GameActivity,
                            getString(R.string.already_guessed),
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {

                        if (response.body()?.correct == false) {
                            //fer que es descarti la lletra o es posi en gris i caigui abre
                            button.foreground.alpha = 140

                        } else {

                        }

                        //The user can not guess a letter that has ben guessed before
                        button.isClickable = false
                    }
                }

                override fun onFailure(call: Call<ResponseGuessLetter>, t: Throwable) {
                    Toast.makeText(
                        this@GameActivity,
                        getString(R.string.error_connection),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun getHint(retrofit: Retrofit) {
        val call = retrofit.create(ApiHangman::class.java)
        call.getHint(gameInfo.token).enqueue(object : Callback<ResponseHint> {
            override fun onResponse(call: Call<ResponseHint>, response: Response<ResponseHint>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<ResponseHint>, t: Throwable) {
                Toast.makeText(
                    this@GameActivity,
                    getString(R.string.error_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }

    fun getSolution(retrofit: Retrofit) {
        val call = retrofit.create(ApiHangman::class.java)
        call.getSolution(gameInfo.token).enqueue(object : Callback<ResponseSolution> {
            override fun onResponse(
                call: Call<ResponseSolution>,
                response: Response<ResponseSolution>
            ) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<ResponseSolution>, t: Throwable) {
                Toast.makeText(
                    this@GameActivity,
                    getString(R.string.error_connection),
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }
}