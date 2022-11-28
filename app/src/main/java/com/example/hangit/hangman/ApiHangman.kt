package com.example.hangit.hangman

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiHangman {

    @POST("/hangman")
    fun createGame() : Call<ResponseCreateGame>

    @PUT("/hangman")
    fun guessLetter(@Query ("letter") letter: String, @Query("token") token: String) : Call<ResponseGuessLetter>

    @GET("/hangman/hint")
    fun getHint(@Query("token") token: String) : Call<ResponseHint>

    @GET("/hangman")
    fun getSolution(@Query("token") token : String) : Call<ResponseSolution>
}