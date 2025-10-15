package com.example.lab08.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingView(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorView(
    message: String = "Ocurrió un error",
    onRetry: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(message, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.height(12.dp))
            if (onRetry != null) {
                Button(onClick = onRetry) { Text("Reintentar") }
            }
        }
    }
}

// Wrappers con tus nombres “antiguos”
@Composable fun Loading(modifier: Modifier = Modifier) = LoadingView(modifier)
@Composable fun Error(message: String = "Ocurrió un error", onRetry: (() -> Unit)? = null, modifier: Modifier = Modifier) =
    ErrorView(message, onRetry, modifier)
