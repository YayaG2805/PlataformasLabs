package com.example.lab08.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lab08.R

@Composable
fun LoginScreen(onStart: () -> Unit) {
    Surface(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pon un PNG en res/drawable con este nombre
            Image(
                painter = painterResource(id = R.drawable.rick_morty_logo),
                contentDescription = "Rick & Morty",
                modifier = Modifier.fillMaxWidth(0.7f).aspectRatio(2.2f),
                contentScale = ContentScale.Fit
            )

            Spacer(Modifier.height(40.dp))

            Button(onClick = onStart, modifier = Modifier.fillMaxWidth(0.6f)) {
                Text("Entrar")
            }

            Spacer(Modifier.height(60.dp))

            Text(
                "Diego Sebastián Guevara - 24128 ",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}
