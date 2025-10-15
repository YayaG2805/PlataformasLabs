package com.example.lab08.ui.locations

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab08.data.repository.ServiceLocator
import com.example.lab08.data.room.entities.LocationEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class LocationDetailState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val data: LocationEntity? = null
)

class LocationDetailViewModel(app: Application, private val id: Int) : AndroidViewModel(app) {
    private val repo = ServiceLocator.provideLocationsRepo(app)
    private val _state = MutableStateFlow(LocationDetailState())
    val state: StateFlow<LocationDetailState> = _state

    init { reload() }

    fun reload() {
        viewModelScope.launch {
            _state.value = LocationDetailState(isLoading = true)
            delay(1200)

            if (id % 2 != 0) { // impar => error
                _state.value = LocationDetailState(isLoading = false, hasError = true)
                return@launch
            }
            repo.getById(id).collectLatest { item ->
                _state.value = LocationDetailState(isLoading = false, data = item)
            }
        }
    }
}
