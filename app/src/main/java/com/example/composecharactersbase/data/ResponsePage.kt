package com.example.composecharactersbase.data

data class ResponsePage(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String
)
