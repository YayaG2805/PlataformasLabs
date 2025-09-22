package com.example.lab08.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

// Helper reutilizable
private inline fun <reified T> routeOf(): String = T::class.qualifiedName!!

@Composable
fun BottomBar(
    navigateToCharacters: () -> Unit,
    navigateToLocations: () -> Unit,
    navigateToProfile: () -> Unit,
    currentRoute: String?
) {
    val selectedCharacters = currentRoute == routeOf<CharactersRoute>()
    val selectedLocations  = currentRoute == routeOf<LocationsRoute>()
    val selectedProfile    = currentRoute == routeOf<ProfileRoute>()

    NavigationBar {
        NavigationBarItem(
            selected = selectedCharacters,
            onClick = { if (!selectedCharacters) navigateToCharacters() },
            icon = { Icon(Icons.Filled.People, contentDescription = null) },
            label = { Text("Personajes") }
        )
        NavigationBarItem(
            selected = selectedLocations,
            onClick = { if (!selectedLocations) navigateToLocations() },
            icon = { Icon(Icons.Filled.LocationOn, contentDescription = null) },
            label = { Text("Ubicaciones") }
        )
        NavigationBarItem(
            selected = selectedProfile,
            onClick = { if (!selectedProfile) navigateToProfile() },
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
            label = { Text("Perfil") }
        )
    }
}
