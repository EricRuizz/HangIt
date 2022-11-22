package com.example.hangit


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityGameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hangman-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        createGame(retrofit)

        //Player guessed letter A
        binding.letterA.setOnClickListener{
            guessLetter(retrofit, "A")
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
                gameInfo = response.body() ?: ResponseCreateGame("", "")
            }

            override fun onFailure(call: Call<ResponseCreateGame>, t: Throwable) {
                Toast.makeText(this@GameActivity, getString(R.string.error_connection), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun guessLetter(retrofit: Retrofit, letter:String) {
        val call = retrofit.create(ApiHangman::class.java)
        call.guessLetter(gameInfo.token, letter)
            .enqueue(object : Callback<ResponseGuessLetter> {
                override fun onResponse(
                    call: Call<ResponseGuessLetter>,
                    response: Response<ResponseGuessLetter>
                ) {
                    if (response.body()?.correct == false) {
                        //fer que es descarti la lletra o es posi en gris i caigui abre
                    }
                }

                override fun onFailure(call: Call<ResponseGuessLetter>, t: Throwable) {
                    Toast.makeText(this@GameActivity, getString(R.string.error_connection), Toast.LENGTH_SHORT).show()
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
                Toast.makeText(this@GameActivity, getString(R.string.error_connection), Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getSolution(retrofit: Retrofit) {
        val call = retrofit.create(ApiHangman::class.java)
        call.getSolution(gameInfo.token).enqueue(object : Callback<ResponseSolution> {
            override fun onResponse(call: Call<ResponseSolution>, response: Response<ResponseSolution>) {
                    TODO("Not yet implemented")
                }

            override fun onFailure(call: Call<ResponseSolution>, t: Throwable) {
                Toast.makeText(this@GameActivity, getString(R.string.error_connection), Toast.LENGTH_SHORT).show()
            }

        })
    }
}