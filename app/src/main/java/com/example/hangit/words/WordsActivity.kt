package com.example.hangit.words

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.hangit.LoginActivity
import com.example.hangit.databinding.ActivitySplashScreenBinding
import com.example.hangit.databinding.ActivityWordsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WordsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityWordsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nextQuote()

        binding.nextWordButton.setOnClickListener{
            nextQuote()
        }
    }

    fun nextQuote() {
        val outside = Retrofit.Builder().baseUrl("http://quotes.stormconsultancy.co.uk/")
            .addConverterFactory(GsonConverterFactory.create()).build()

        val service = outside.create(ApiQuotes::class.java)
        service.getQuote().enqueue(object: Callback<Quote>{
            override fun onResponse(call: Call<Quote>, response: Response<Quote>) {
                val quote = response.body()
                binding.quoteText.text = quote?.text?: "Something went wrong :("
                binding.autorText.text = quote?.author?: ""
            }

            override fun onFailure(call: Call<Quote>, t: Throwable) {
                binding.quoteText.text = "Something went wrong :("
                binding.autorText.text = ""
            }

        })
    }

}