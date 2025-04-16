package com.example.composecharactersbase.data.remote.api

import com.example.composecharactersbase.data.model.Character
import retrofit2.http.GET

interface ApiService {
    @GET("character")
    suspend fun getAllCharacters(): List<Character>
}