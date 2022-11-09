package com.example.hangit


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.hangit.databinding.ActivityGameBinding
import com.example.hangit.hangman.ApiHangman
import com.example.hangit.hangman.ResponseCreateGame
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameActivity : AppCompatActivity() {

    private lateinit var binding : ActivityGameBinding
    private lateinit var gameInfo : ResponseCreateGame


    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityGameBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://hangman-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        CreateGame(retrofit)
    }

    fun CreateGame(retrofit: Retrofit){

        val call = retrofit.create(ApiHangman::class.java)
        call.createGame().enqueue( object : Callback<ResponseCreateGame>{
            override fun onResponse(
                call: Call<ResponseCreateGame>,
                response: Response<ResponseCreateGame>
            ) {
                gameInfo = response.body()?: ResponseCreateGame("","")
            }

            override fun onFailure(call: Call<ResponseCreateGame>, t: Throwable) {
                Toast.makeText(this@GameActivity, "There was an error", Toast.LENGTH_SHORT).show()
            }

        })
    }


}