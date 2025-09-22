package com.example.lab08.ui.characters

import com.example.lab08.data.Character

data class CharacterDetailState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val data: Character? = null
)
