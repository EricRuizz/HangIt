package com.example.hangit


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
    private lateinit var hintInfo: ResponseHint
    private lateinit var solutionInfo: ResponseSolution

    private var failGuess: Int = 0
    private val MAX_ERRORS: Int = 10
    private var gameOver: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityGameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hangman-api.herokuapp.com")
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

        //Go to back the main screen
        binding.goBackButtonGame.setOnClickListener {
            val intent = Intent(this@GameActivity, MainActivity::class.java)
            startActivity(intent)

            finish()
        }
    }


    //API Hangman
    fun createGame(retrofit: Retrofit) {

        createLetters()

        val call = retrofit.create(ApiHangman::class.java)
        call.createGame().enqueue(object : Callback<ResponseCreateGame> {
            override fun onResponse(
                call: Call<ResponseCreateGame>,
                response: Response<ResponseCreateGame>
            ) {
                //Save the game info and check if null
                gameInfo = response.body() ?: ResponseCreateGame("", "")
                binding.word.text = gameInfo.hangman

                getSolution(retrofit)
                gameOver = false
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

    //User enters a letter
    fun guessLetter(retrofit: Retrofit, letter: String, button: Button) {
        val call = retrofit.create(ApiHangman::class.java)
        call.guessLetter(letter, gameInfo.token)
            .enqueue(object : Callback<ResponseGuessLetter> {
                override fun onResponse(
                    call: Call<ResponseGuessLetter>,
                    response: Response<ResponseGuessLetter>
                ) {
                    if (!gameOver) {
                        //Save the letter info and check if null
                        letterInfo = response.body() ?: ResponseGuessLetter("", "", false)
                        gameInfo.token = letterInfo.token
                        gameInfo.hangman = letterInfo.hangman

                        if (response.body()?.correct == false) {
                            // "tree animation" plays and sound
                            binding.treeRope.scaleY += 0.2f
                            binding.treeRope.y += 5.5f
                            binding.treeWheel.y += 10.5f

                            //Updated the number of errors and check if user lost
                            failGuess++
                            if (failGuess >= MAX_ERRORS) {
                                getSolution(retrofit)

                                //Wait 2 sec and go to the Lost Screen
                                Handler().postDelayed(
                                    {
                                        val intent =
                                            Intent(this@GameActivity, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }, 2000
                                )
                            }


                        } else {
                            //Add sound

                            //Add letter to the solution
                            binding.word.text = letterInfo.hangman

                            //Check  if user has won
                            if (letterInfo.hangman == solutionInfo.solution) {

                                //Wait 2 sec and go to the Win Screen
                                Handler().postDelayed(
                                    {
                                        val intent =
                                            Intent(this@GameActivity, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }, 2000
                                )
                            }
                        }

                        //We discard the letter
                        button.background.alpha = 0
                        button.text = " "
                        button.foreground.alpha = 80

                        //The user can not guess a letter that has been guessed before
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

    //User can ask for a hint (not implemented now)
    fun getHint(retrofit: Retrofit) {
        val call = retrofit.create(ApiHangman::class.java)
        call.getHint(gameInfo.token).enqueue(object : Callback<ResponseHint> {
            override fun onResponse(call: Call<ResponseHint>, response: Response<ResponseHint>) {
                //Save the game info and check if null
                hintInfo = response.body() ?: ResponseHint("", "")
                gameInfo.token = hintInfo.token
                //guessLetter(retrofit, hintInfo.letter, button)
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

    //Get the solution: the word
    fun getSolution(retrofit: Retrofit) {
        val call = retrofit.create(ApiHangman::class.java)
        call.getSolution(gameInfo.token).enqueue(object : Callback<ResponseSolution> {
            override fun onResponse(
                call: Call<ResponseSolution>,
                response: Response<ResponseSolution>
            ) {
                solutionInfo = response.body() ?: ResponseSolution("", "")
                gameInfo.token = solutionInfo.token

                if (failGuess >= MAX_ERRORS) {
                    gameInfo.hangman = solutionInfo.solution
                    binding.word.text = gameInfo.hangman
                }

                gameOver = true

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

    fun createLetters() {

        //We set the letter parameters
        binding.letterA.background.alpha = 0
        binding.letterA.text = " "
        binding.letterA.foreground.alpha = 255
        binding.letterA.isClickable = true

        binding.letterB.background.alpha = 0
        binding.letterB.text = " "
        binding.letterB.foreground.alpha = 255
        binding.letterB.isClickable = true

        binding.letterC.background.alpha = 0
        binding.letterC.text = " "
        binding.letterC.foreground.alpha = 255
        binding.letterC.isClickable = true

        binding.letterD.background.alpha = 0
        binding.letterD.text = " "
        binding.letterD.foreground.alpha = 255
        binding.letterD.isClickable = true

        binding.letterE.background.alpha = 0
        binding.letterE.text = " "
        binding.letterE.foreground.alpha = 255
        binding.letterE.isClickable = true

        binding.letterF.background.alpha = 0
        binding.letterF.text = " "
        binding.letterF.foreground.alpha = 255
        binding.letterF.isClickable = true

        binding.letterG.background.alpha = 0
        binding.letterG.text = " "
        binding.letterG.foreground.alpha = 255
        binding.letterG.isClickable = true

        binding.letterH.background.alpha = 0
        binding.letterH.text = " "
        binding.letterH.foreground.alpha = 255
        binding.letterH.isClickable = true

        binding.letterI.background.alpha = 0
        binding.letterI.text = " "
        binding.letterI.foreground.alpha = 255
        binding.letterI.isClickable = true

        binding.letterJ.background.alpha = 0
        binding.letterJ.text = " "
        binding.letterJ.foreground.alpha = 255
        binding.letterJ.isClickable = true

        binding.letterK.background.alpha = 0
        binding.letterK.text = " "
        binding.letterK.foreground.alpha = 255
        binding.letterK.isClickable = true

        binding.letterL.background.alpha = 0
        binding.letterL.text = " "
        binding.letterL.foreground.alpha = 255
        binding.letterL.isClickable = true

        binding.letterM.background.alpha = 0
        binding.letterM.text = " "
        binding.letterM.foreground.alpha = 255
        binding.letterM.isClickable = true

        binding.letterN.background.alpha = 0
        binding.letterN.text = " "
        binding.letterN.foreground.alpha = 255
        binding.letterN.isClickable = true

        binding.letterO.background.alpha = 0
        binding.letterO.text = " "
        binding.letterO.foreground.alpha = 255
        binding.letterO.isClickable = true

        binding.letterP.background.alpha = 0
        binding.letterP.text = " "
        binding.letterP.foreground.alpha = 255
        binding.letterP.isClickable = true

        binding.letterQ.background.alpha = 0
        binding.letterQ.text = " "
        binding.letterQ.foreground.alpha = 255
        binding.letterQ.isClickable = true

        binding.letterR.background.alpha = 0
        binding.letterR.text = " "
        binding.letterR.foreground.alpha = 255
        binding.letterR.isClickable = true

        binding.letterS.background.alpha = 0
        binding.letterS.text = " "
        binding.letterS.foreground.alpha = 255
        binding.letterS.isClickable = true

        binding.letterT.background.alpha = 0
        binding.letterT.text = " "
        binding.letterT.foreground.alpha = 255
        binding.letterT.isClickable = true

        binding.letterU.background.alpha = 0
        binding.letterU.text = " "
        binding.letterU.foreground.alpha = 255
        binding.letterU.isClickable = true

        binding.letterV.background.alpha = 0
        binding.letterV.text = " "
        binding.letterV.foreground.alpha = 255
        binding.letterV.isClickable = true

        binding.letterW.background.alpha = 0
        binding.letterW.text = " "
        binding.letterW.foreground.alpha = 255
        binding.letterW.isClickable = true

        binding.letterX.background.alpha = 0
        binding.letterX.text = " "
        binding.letterX.foreground.alpha = 255
        binding.letterX.isClickable = true

        binding.letterY.background.alpha = 0
        binding.letterY.text = " "
        binding.letterY.foreground.alpha = 255
        binding.letterY.isClickable = true

        binding.letterZ.background.alpha = 0
        binding.letterZ.text = " "
        binding.letterZ.foreground.alpha = 255
        binding.letterZ.isClickable = true

        binding.space.background.alpha = 0
        binding.space.text = " "
        binding.space.foreground.alpha = 255
        binding.space.isClickable = true


    }
}