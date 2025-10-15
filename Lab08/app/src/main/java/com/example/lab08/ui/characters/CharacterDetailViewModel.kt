package com.example.lab08.ui.characters

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab08.data.repository.ServiceLocator
import com.example.lab08.data.room.entities.CharacterEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val data: CharacterEntity? = null
)

class CharacterDetailViewModel(app: Application, private val id: Int) : AndroidViewModel(app) {
    private val repo = ServiceLocator.provideCharactersRepo(app)
    private val _state = MutableStateFlow(CharacterDetailState())
    val state: StateFlow<CharacterDetailState> = _state

    init { reload() }

    fun reload() {
        viewModelScope.launch {
            _state.value = CharacterDetailState(isLoading = true)
            delay(1200) // simulación de carga

            if (id % 2 != 0) { // impar => error
                _state.value = CharacterDetailState(isLoading = false, hasError = true)
                return@launch
            }
            repo.getById(id).collectLatest { item ->
                _state.value = CharacterDetailState(isLoading = false, data = item)
            }
        }
    }
}
