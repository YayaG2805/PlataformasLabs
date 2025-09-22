@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(nombreCompleto: String, carne: String, onLogout: () -> Unit) {
    Surface(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            TopAppBar(title = { Text("Profile") })
            Spacer(Modifier.height(24.dp))
            Box(
                modifier = Modifier.size(120.dp).clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) { Icon(Icons.Filled.Person, null, tint = MaterialTheme.colorScheme.primary) }
            Spacer(Modifier.height(24.dp))
            InfoRow("Nombre:", nombreCompleto)
            InfoRow("Carné:", carne)
            Spacer(Modifier.height(24.dp))
            Button(onClick = onLogout) { Text("Cerrar sesión") }
        }
    }
}

@Composable private fun InfoRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 8.dp)) {
        Text(label, modifier = Modifier.weight(1f))
        Text(value, modifier = Modifier.weight(1f))
    }
}
