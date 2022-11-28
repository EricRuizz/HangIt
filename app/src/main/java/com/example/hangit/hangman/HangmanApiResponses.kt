package com.example.hangit.hangman


//Post
data class ResponseCreateGame(
    var hangman: String,
    var token: String
)

//Put
data class ResponseGuessLetter(
    val hangman: String,
    val token: String,
    val correct: Boolean
)

//Get
data class ResponseHint(
    val letter: String,
    val token: String
)

data class ResponseSolution(
    val solution: String,
    val token: String
)