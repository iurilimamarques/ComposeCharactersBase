package com.example.composecharactersbase.data.remote.api

import com.example.composecharactersbase.data.Response
import com.example.composecharactersbase.data.model.Character
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("character")
    suspend fun getAllCharacters(): Response<Character>

    @GET("character/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Character
}