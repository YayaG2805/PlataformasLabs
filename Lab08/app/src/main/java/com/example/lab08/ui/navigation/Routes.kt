package com.example.lab08.ui.navigation

import kotlinx.serialization.Serializable

@Serializable object LoginRoute
@Serializable object ProfileRoute

@Serializable object CharactersRoute
@Serializable data class CharacterDetailRoute(val id: Int)

@Serializable object LocationsRoute
@Serializable data class LocationDetailRoute(val id: Int)
