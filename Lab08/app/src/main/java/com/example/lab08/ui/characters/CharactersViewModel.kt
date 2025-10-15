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

data class CharactersState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val data: List<CharacterEntity> = emptyList()
)

class CharactersViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = ServiceLocator.provideCharactersRepo(app)
    private val _state = MutableStateFlow(CharactersState())
    val state: StateFlow<CharactersState> = _state

    init { reload() }

    fun reload() {
        viewModelScope.launch {
            _state.value = CharactersState(isLoading = true)
            delay(1500) // simulación de carga

            // semilla “número” para par/impar (impar => error)
            val seed = (System.currentTimeMillis() / 1000).toInt()
            if (seed % 2 != 0) {
                _state.value = CharactersState(isLoading = false, hasError = true)
                return@launch
            }

            repo.getAll().collectLatest { list ->
                _state.value = CharactersState(isLoading = false, data = list)
            }
        }
    }
}
