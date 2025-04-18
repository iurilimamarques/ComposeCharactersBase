package com.example.composecharactersbase.domain.repository

import com.example.composecharactersbase.data.Response
import com.example.composecharactersbase.data.model.Character
import com.example.composecharactersbase.data.remote.api.ApiService
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getAllCharacters(): Response<Character> {
        return apiService.getAllCharacters()
    }

    suspend fun getCharacterById(id: Int): Character {
        return apiService.getCharacterById(id)
    }
}