package com.example.hangit.hangman

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiHangman {

    @POST("/hangman")
    fun createGame() : Call<ResponseCreateGame>

    @PUT("/hangman")
    fun guessLetter(@Body letterBody: BodyLetter) : Call<ResponseGuessLetter>

}