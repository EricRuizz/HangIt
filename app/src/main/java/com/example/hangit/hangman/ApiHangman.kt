package com.example.hangit.hangman

import retrofit2.Call
import retrofit2.http.POST

interface ApiHangman {

    @POST("/hangman")
    fun createGame() : Call<ResponseCreateGame>

}