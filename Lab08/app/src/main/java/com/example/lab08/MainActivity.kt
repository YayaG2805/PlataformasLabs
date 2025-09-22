package com.example.lab08

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.lab08.ui.navigation.AppNav
import com.example.lab08.ui.theme.Lab08Theme // si no existe, cambia abajo a MaterialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Usa tu tema; si no tienes Lab08Theme, reemplaza Lab08Theme { ... } por MaterialTheme { ... }
            Lab08Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNav()
                }
            }
        }
    }
}
