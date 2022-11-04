package com.example.hangit.words

import com.google.gson.annotations.SerializedName

data class Quote (
    val author: String,
    val id: String,

    @SerializedName("quote")
    val text: String
) {


}
