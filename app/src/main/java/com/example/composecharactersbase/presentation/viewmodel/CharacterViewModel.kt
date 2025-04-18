package com.example.composecharactersbase.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.composecharactersbase.data.model.Character
import com.example.composecharactersbase.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository,
    application: Application
) : AndroidViewModel(application) {

    private val _characterDetail = MutableLiveData<Character>()
    val characterDetail: LiveData<Character> = _characterDetail

    private val prefs = application.getSharedPreferences("compose_character_prefs", Context.MODE_PRIVATE)
    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchAllCharacters() {
        viewModelScope.launch {
            try {
                val result = repository.getAllCharacters()
                for (c in result.results) {
                    c.favorite = getCharacterFavoriteInfo(c)
                }
                _characters.value = result.results
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }

    fun getCharacterFavoriteInfo(character: Character): Boolean {
        return prefs.getBoolean("character_ID_${character.id}", false)
    }

    fun setCharacterFavorite(character: Character) {
        prefs.edit().putBoolean("character_ID_${character.id}", character.favorite).apply()
    }

    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getCharacterById(id)
                _characterDetail.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }

}