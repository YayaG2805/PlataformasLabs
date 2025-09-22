package com.example.lab08.ui.characters

import com.example.lab08.data.Character

data class CharactersState(
    val isLoading: Boolean = true,
    val hasError: Boolean = false,
    val data: List<Character> = emptyList()
)
