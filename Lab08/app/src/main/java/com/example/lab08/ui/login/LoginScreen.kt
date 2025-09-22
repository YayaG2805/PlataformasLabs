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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.lab08.R

@Composable
fun LoginScreen(
    userName: String,
    userId: String = "24128",                // cambia si necesitas
    onGoToPeople: () -> Unit,                // navegación al flujo principal
    onGoToLocations: () -> Unit = {}         // opcional (no se usa en este mock)
) {
    Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Box(Modifier.fillMaxSize()) {
            // Centro: Logo y botón "Entrar"
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rick_morty_logo),
                    contentDescription = "Rick & Morty",
                    modifier = Modifier.size(180.dp)
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = onGoToPeople,
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(44.dp)
                ) {
                    Text("Entrar")
                }
            }

            // Abajo: nombre y carné (alineado al centro)
            Text(
                text = "$userName  -  #$userId",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp)
            )
        }
    }
}
