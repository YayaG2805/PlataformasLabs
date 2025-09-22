@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.example.lab08.ui.characters.*
import com.example.lab08.ui.locations.*
import com.example.lab08.ui.login.LoginRoute
import com.example.lab08.ui.login.LoginScreen
import com.example.lab08.ui.profile.ProfileRoute
import com.example.lab08.ui.profile.ProfileScreen
import com.example.lab08.ui.theme.Lab08Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Lab08Theme { AppNav() } }
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryAsState()
    val dest = backStack?.destination

    // Helper sin hasRoute()
    val showBottom = when {
        dest.isRoute<LoginRoute>() -> false
        dest.isRoute<CharacterDetailRoute>() -> false
        dest.isRoute<LocationDetailRoute>() -> false
        else -> true
    }

    Scaffold(
        bottomBar = {
            if (showBottom) {
                NavigationBar {
                    NavigationBarItem(
                        selected = dest.isRoute<CharactersRoute>(),
                        onClick = { nav.navigate(CharactersRoute) { launchSingleTop = true } },
                        icon = { Icon(Icons.Filled.People, null) },
                        label = { Text("Characters") }
                    )
                    NavigationBarItem(
                        selected = dest.isRoute<LocationsRoute>(),
                        onClick = { nav.navigate(LocationsRoute) { launchSingleTop = true } },
                        icon = { Icon(Icons.Filled.Place, null) },
                        label = { Text("Locations") }
                    )
                    NavigationBarItem(
                        selected = dest.isRoute<ProfileRoute>(),
                        onClick = { nav.navigate(ProfileRoute) { launchSingleTop = true } },
                        icon = { Icon(Icons.Filled.Person, null) },
                        label = { Text("Profile") }
                    )
                }
            }
        }
    ) { inner ->
        NavHost(navController = nav, startDestination = LoginRoute, modifier = Modifier.padding(inner)) {

            // LOGIN
            composable<LoginRoute> {
                LoginScreen(
                    onStart = {
                        nav.navigate(CharactersRoute) {
                            popUpTo<LoginRoute> { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }

            // ===== Characters (nested) =====
            navigation<CharactersGraph>(startDestination = CharactersRoute) {
                composable<CharactersRoute> {
                    BackHandler { (nav.context as? Activity)?.finish() }
                    CharactersScreen { id -> nav.navigate(CharacterDetailRoute(id)) }
                }
                composable<CharacterDetailRoute> { entry ->
                    val route: CharacterDetailRoute = entry.toRoute()
                    CharacterDetailScreen(id = route.id) { nav.popBackStack() }
                }
            }

            // ===== Locations (nested) =====
            navigation<LocationsGraph>(startDestination = LocationsRoute) {
                composable<LocationsRoute> {
                    BackHandler { (nav.context as? Activity)?.finish() }
                    LocationsScreen { id -> nav.navigate(LocationDetailRoute(id)) }
                }
                composable<LocationDetailRoute> { entry ->
                    val route: LocationDetailRoute = entry.toRoute()
                    LocationDetailScreen(id = route.id) { nav.popBackStack() }
                }
            }

            // ===== Profile =====
            composable<ProfileRoute> {
                BackHandler { (nav.context as? Activity)?.finish() }
                ProfileScreen(
                    nombreCompleto = "Diego Sebastián Guevara",
                    carne = "24128",
                    onLogout = {
                        nav.navigate(LoginRoute) {
                            popUpTo(0) { inclusive = true } // limpia todo el backstack
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

// Helper: detecta si el destino coincide con la ruta tipada T (sin hasRoute)
private inline fun <reified T> NavDestination?.isRoute(): Boolean {
    val expected = T::class.qualifiedName ?: return false
    return this?.route?.startsWith(expected) == true
}
