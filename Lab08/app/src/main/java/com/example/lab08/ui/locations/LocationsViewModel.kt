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

data class LocationsState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val data: List<LocationEntity> = emptyList()
)

class LocationsViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = ServiceLocator.provideLocationsRepo(app)
    private val _state = MutableStateFlow(LocationsState())
    val state: StateFlow<LocationsState> = _state

    init { reload() }

    fun reload() {
        viewModelScope.launch {
            _state.value = LocationsState(isLoading = true)
            delay(1500) // simulación de carga

            val seed = (System.currentTimeMillis() / 1000).toInt()
            if (seed % 2 != 0) {
                _state.value = LocationsState(isLoading = false, hasError = true)
                return@launch
            }

            repo.getAll().collectLatest { list ->
                _state.value = LocationsState(isLoading = false, data = list)
            }
        }
    }
}
