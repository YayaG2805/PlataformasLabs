package com.example.lab08.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CharactersResponseDto(
    val info: PageInfoDto,
    val results: List<CharacterDto>
)

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    @SerialName("image") val imageUrl: String
)

@Serializable
data class PageInfoDto(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)
