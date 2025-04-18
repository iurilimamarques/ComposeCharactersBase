package com.example.composecharactersbase.data.model

data class Character (
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val image: String,
    var favorite: Boolean = false
)