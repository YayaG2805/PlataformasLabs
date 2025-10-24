package com.example.lab08.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationsResponseDto(
    val info: PageInfoDto,
    val results: List<LocationDto>
)

@Serializable
data class LocationDto(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)
