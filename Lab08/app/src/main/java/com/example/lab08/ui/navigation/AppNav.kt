package com.example.lab08.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.lab08.ui.characters.CharacterDetailScreen
import com.example.lab08.ui.characters.CharactersScreen
import com.example.lab08.ui.locations.LocationDetailScreen
import com.example.lab08.ui.locations.LocationsScreen
import com.example.lab08.ui.login.LoginScreen
import com.example.lab08.ui.profile.ProfileScreen
import com.example.lab08.ui.splash.SplashScreen

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Rutas donde queremos mostrar la BottomBar
    val showBottomBar = currentRoute in setOf(
        routeOf<CharactersRoute>(),
        routeOf<LocationsRoute>(),
        routeOf<ProfileRoute>()
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    navigateToCharacters = {
                        nav.navigate(CharactersRoute) {
                            launchSingleTop = true
                        }
                    },
                    navigateToLocations = {
                        nav.navigate(LocationsRoute) {
                            launchSingleTop = true
                        }
                    },
                    navigateToProfile = {
                        nav.navigate(ProfileRoute) {
                            launchSingleTop = true
                        }
                    },
                    currentRoute = currentRoute
                )
            }
        }
    ) { inner ->
        NavHost(
            navController = nav,
            startDestination = SplashRoute,
            modifier = Modifier.padding(inner) // content bajo la BottomBar
        ) {
            composable<SplashRoute> {
                SplashScreen(
                    onGoLogin = { nav.navigate(LoginRoute) { popUpTo(0) } },
                    onGoHome  = { nav.navigate(CharactersRoute) { popUpTo(0) } }
                )
            }
            composable<LoginRoute> {
                LoginScreen(onLoggedIn = { nav.navigate(CharactersRoute) { popUpTo(0) } })
            }

            // Tabs
            composable<CharactersRoute> {
                CharactersScreen(
                    onCharacterClick = { id -> nav.navigate(CharacterDetailRoute(id)) },
                    onLocationsClick = { nav.navigate(LocationsRoute) },
                    onProfileClick   = { nav.navigate(ProfileRoute) }
                )
            }
            composable<LocationsRoute> {
                LocationsScreen(
                    onLocationClick   = { id -> nav.navigate(LocationDetailRoute(id)) },
                    onCharactersClick = { nav.navigate(CharactersRoute) },
                    onProfileClick    = { nav.navigate(ProfileRoute) }
                )
            }
            composable<ProfileRoute> {
                ProfileScreen(
                    onLogout         = { nav.navigate(LoginRoute) { popUpTo(0) } },
                    onOpenCharacters = { nav.navigate(CharactersRoute) },
                    onOpenLocations  = { nav.navigate(LocationsRoute) }
                )
            }

            // Details (sin BottomBar)
            composable<CharacterDetailRoute> { entry ->
                val args = entry.toRoute<CharacterDetailRoute>()
                CharacterDetailScreen(id = args.id, onBack = { nav.popBackStack() })
            }
            composable<LocationDetailRoute> { entry ->
                val args = entry.toRoute<LocationDetailRoute>()
                LocationDetailScreen(id = args.id, onBack = { nav.popBackStack() })
            }
        }
    }
}
