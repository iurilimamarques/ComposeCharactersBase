package com.example.composecharactersbase.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composecharactersbase.data.model.Character
import com.example.composecharactersbase.domain.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    private val _characters = MutableLiveData<List<Character>>()
    val characters: LiveData<List<Character>> = _characters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchUser() {
        viewModelScope.launch {
            try {
                val result = repository.getCharacters()
                _characters.value = result
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            }
        }
    }
}