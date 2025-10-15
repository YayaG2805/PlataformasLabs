package com.example.lab08.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.lab08.data.repository.ServiceLocator
import kotlinx.coroutines.flow.first

@Composable
fun SplashScreen(onGoLogin: () -> Unit, onGoHome: () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val name = ServiceLocator.provideUserPrefs(context).userName.first()
        if (name.isNullOrBlank()) onGoLogin() else onGoHome()
    }
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
