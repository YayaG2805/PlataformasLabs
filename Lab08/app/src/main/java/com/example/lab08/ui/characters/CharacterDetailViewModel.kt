package com.example.lab08.ui.characters

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab08.data.CharacterDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class CharacterDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val db: CharacterDb = CharacterDb()
) : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state = _state.asStateFlow()

    private val id: Int = savedStateHandle.get<Int>("id") ?: -1

    init { load() }

    fun load() {
        _state.value = CharacterDetailState(isLoading = true)
        viewModelScope.launch {
            delay(2000) // 2 s
            val n = Random.nextInt(1, 11)
            if (n % 2 == 0) {
                _state.value = CharacterDetailState(
                    isLoading = false,
                    data = db.getCharacterById(id)
                )
            } else {
                _state.value = CharacterDetailState(isLoading = false, hasError = true)
            }
        }
    }
}
