package com.example.lab08.ui.navigation

import androidx.navigation.toRoute
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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

    NavHost(navController = nav, startDestination = SplashRoute) {

        composable<SplashRoute> {
            SplashScreen(
                onGoLogin = {
                    nav.navigate(LoginRoute) { popUpTo(0); launchSingleTop = true; restoreState = false }
                },
                onGoHome  = {
                    nav.navigate(CharactersRoute) { popUpTo(0); launchSingleTop = true; restoreState = false }
                }
            )
        }

        composable<LoginRoute> {
            LoginScreen(
                onLoggedIn = {
                    nav.navigate(CharactersRoute) { popUpTo(0); launchSingleTop = true; restoreState = false }
                }
            )
        }

        composable<CharactersRoute> {
            Scaffold { inner ->
                Box(Modifier.padding(inner)) {
                    CharactersScreen(
                        onCharacterClick = { id -> nav.navigate(CharacterDetailRoute(id)) },
                        onLocationsClick = { nav.navigate(LocationsRoute) },
                        onProfileClick   = { nav.navigate(ProfileRoute) }
                    )
                }
            }
        }

        composable<CharacterDetailRoute> { entry ->
            val args = entry.toRoute<CharacterDetailRoute>()
            CharacterDetailScreen(id = args.id, onBack = { nav.popBackStack() })
        }

        composable<LocationsRoute> {
            LocationsScreen(
                onLocationClick  = { id -> nav.navigate(LocationDetailRoute(id)) },
                onCharactersClick = { nav.navigate(CharactersRoute) },
                onProfileClick    = { nav.navigate(ProfileRoute) }
            )
        }

        composable<LocationDetailRoute> { entry ->
            val args = entry.toRoute<LocationDetailRoute>()
            LocationDetailScreen(id = args.id, onBack = { nav.popBackStack() })
        }

        composable<ProfileRoute> {
            ProfileScreen(
                onLogout         = { nav.navigate(LoginRoute) { popUpTo(0); launchSingleTop = true; restoreState = false } },
                onOpenCharacters = { nav.navigate(CharactersRoute) },
                onOpenLocations  = { nav.navigate(LocationsRoute) }
            )
        }
    }
}
