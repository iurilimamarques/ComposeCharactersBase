package com.example.composecharactersbase.domain.repository

import com.example.composecharactersbase.data.model.Character
import com.example.composecharactersbase.data.remote.api.ApiService
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCharacters(): List<Character> {
        return apiService.getAllCharacters()
    }
}