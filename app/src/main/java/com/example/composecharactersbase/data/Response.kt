package com.example.composecharactersbase.data

data class Response<T>(
    val info: ResponsePage,
    val results: List<T>
)