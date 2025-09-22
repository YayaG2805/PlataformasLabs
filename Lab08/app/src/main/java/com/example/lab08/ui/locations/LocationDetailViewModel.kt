package com.example.lab08.ui.locations

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab08.data.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LocationDetailViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val db: LocationDb = LocationDb()
) : ViewModel() {

    private val _state = MutableStateFlow(LocationDetailState())
    val state = _state.asStateFlow()

    private val id: Int = savedStateHandle.get<Int>("id") ?: -1

    init { load() }

    fun load() {
        _state.value = LocationDetailState(isLoading = true)
        viewModelScope.launch {
            delay(2000)
            val n = Random.nextInt(1, 11)
            if (n % 2 == 0) {
                _state.value = LocationDetailState(
                    isLoading = false,
                    data = db.getLocationById(id)
                )
            } else {
                _state.value = LocationDetailState(isLoading = false, hasError = true)
            }
        }
    }
}
