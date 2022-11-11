package com.example.hangit.hangman

data class ResponseCreateGame(

    val hangman: String,
    val token: String

)

data class ResponseGuessLetter(
    val hangman: String,
    val token: String,
    val correct: Boolean
)
