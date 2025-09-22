package com.example.lab08.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab08.data.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class CharactersViewModel(
    private val db: CharacterDb = CharacterDb()
) : ViewModel() {

    private val _state = MutableStateFlow(CharactersState())
    val state = _state.asStateFlow()

    init { load() }

    fun load() {
        _state.value = CharactersState(isLoading = true)
        viewModelScope.launch {
            delay(4000) // 4 s
            val n = Random.nextInt(1, 11)
            if (n % 2 == 0) {
                _state.value = CharactersState(
                    isLoading = false,
                    hasError = false,
                    data = db.getAllCharacters()
                )
            } else {
                _state.value = CharactersState(isLoading = false, hasError = true)
            }
        }
    }
}
