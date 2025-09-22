package com.example.lab08.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lab08.ui.characters.CharacterDetailScreen
import com.example.lab08.ui.characters.CharactersScreen
import com.example.lab08.ui.locations.LocationDetailScreen
import com.example.lab08.ui.locations.LocationsScreen
import com.example.lab08.ui.login.LoginScreen
import com.example.lab08.ui.profile.ProfileScreen

// Helper: nombre calificado de la ruta tipada @Serializable
private inline fun <reified T> routeOf(): String = T::class.qualifiedName!!

@Composable
fun AppNav() {
    val nav = rememberNavController()
    val backStackEntry by nav.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Ocultar BottomBar en Login y en pantallas de detalle
    val showBottomBar = currentRoute !in setOf(
        routeOf<LoginRoute>(),
        routeOf<CharacterDetailRoute>(),
        routeOf<LocationDetailRoute>()
    )

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomBar(
                    navigateToCharacters = { nav.navigate(CharactersRoute) },
                    navigateToLocations  = { nav.navigate(LocationsRoute) },
                    navigateToProfile    = { nav.navigate(ProfileRoute) },
                    currentRoute = currentRoute
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = LoginRoute,
            modifier = Modifier.padding(padding)
        ) {
            // LOGIN (mock: logo + botón "Entrar" y nombre/carné abajo)
            composable<LoginRoute> {
                LoginScreen(
                    userName = "Diego Sebastián Guevara Casasola",
                    userId   = "24128",
                    onGoToPeople = { nav.navigate(CharactersRoute) }
                )
            }

            // LISTAS
            composable<CharactersRoute> {
                CharactersScreen(onCharacterClick = { id -> nav.navigate(CharacterDetailRoute(id)) })
            }
            composable<LocationsRoute> {
                LocationsScreen(onLocationClick = { id -> nav.navigate(LocationDetailRoute(id)) })
            }

            // DETALLES (con SavedStateHandle leyendo "id")
            composable<CharacterDetailRoute> {
                CharacterDetailScreen(onBack = { nav.popBackStack() })
            }
            composable<LocationDetailRoute> {
                LocationDetailScreen(onBack = { nav.popBackStack() })
            }

            // PROFILE (avatar + nombre + carné + cerrar sesión)
            composable<ProfileRoute> {
                ProfileScreen(
                    fullName = "Diego Sebastián Guevara Casasola",
                    carnet   = "24128",
                    onLogout = {
                        // Regresar al Login y limpiar histórico básico
                        nav.navigate(LoginRoute) {
                            // Este popUpTo(0) limpia el back stack; si en tu versión no aplica,
                            // podemos hacer un navigate con flags equivalentes usando activity finish.
                            popUpTo(0)
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                )
            }
        }
    }
}
