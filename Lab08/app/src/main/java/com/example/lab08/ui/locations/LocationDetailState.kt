package com.example.lab08.ui.locations

import com.example.lab08.data.Location

data class LocationDetailState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val data: Location? = null
)
