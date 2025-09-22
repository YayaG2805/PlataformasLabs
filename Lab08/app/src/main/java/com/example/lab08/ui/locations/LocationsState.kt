package com.example.lab08.ui.locations

import com.example.lab08.data.Location

data class LocationsState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val data: List<Location> = emptyList()
)
