package com.example.lab08.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab08.data.LocationDb
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class LocationsViewModel(
    private val db: LocationDb = LocationDb()
) : ViewModel() {
    private val _state = MutableStateFlow(LocationsState())
    val state = _state.asStateFlow()

    init { load() }

    fun load() {
        _state.value = LocationsState(isLoading = true)
        viewModelScope.launch {
            delay(4000)
            val n = Random.nextInt(1, 11)
            if (n % 2 == 0) {
                _state.value = LocationsState(
                    isLoading = false,
                    data = db.getAllLocations()
                )
            } else {
                _state.value = LocationsState(isLoading = false, hasError = true)
            }
        }
    }
}
