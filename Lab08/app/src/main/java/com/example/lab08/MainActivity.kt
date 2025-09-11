package com.example.lab08

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.lab08.ui.characters.CharactersRoute
import com.example.lab08.ui.characters.CharactersScreen
import com.example.lab08.ui.detail.CharacterDetailRoute
import com.example.lab08.ui.detail.CharacterDetailScreen
import com.example.lab08.ui.login.LoginRoute
import com.example.lab08.ui.login.LoginScreen
import com.example.lab08.ui.theme.Lab08Theme  // usa tu tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab08Theme {      // si tu función se llama distinto, cámbiala aquí
                AppNav()
            }
        }
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = LoginRoute) {

        // LOGIN (no queda en backstack)
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

        // CHARACTERS (back = cerrar app)
        composable<CharactersRoute> {
            BackHandler {
                (nav.context as? Activity)?.finish()
            }
            CharactersScreen(
                onCharacterClick = { id -> nav.navigate(CharacterDetailRoute(id)) }
            )
        }

        // DETAIL (recibe solo ID)
        composable<CharacterDetailRoute> { backStackEntry ->
            val route: CharacterDetailRoute = backStackEntry.toRoute()
            CharacterDetailScreen(
                id = route.id,
                onBack = { nav.popBackStack() }
            )
        }
    }
}
