package com.example.hangit.nasa

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiNasa {
    @GET("search")
    fun performSearch(@Query("q") query:String): Call<>  //al hangman li haurem de pasar un altre parametre pq es un document json

}