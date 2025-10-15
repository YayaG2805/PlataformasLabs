package com.example.lab08.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lab08.data.repository.ServiceLocator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    onLogout: () -> Unit,
    onOpenCharacters: () -> Unit,
    onOpenLocations: () -> Unit,
    carnet: String = "24128"
) {
    val context = LocalContext.current
    val prefs = remember { ServiceLocator.provideUserPrefs(context) }
    var fullName by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        prefs.userName.collectLatest { fullName = it ?: "" }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = false,
                    onClick = onOpenCharacters,
                    icon = { /* add icon if you want */ },
                    label = { Text("Characters") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = onOpenLocations,
                    icon = { /* add icon if you want */ },
                    label = { Text("Locations") }
                )
                NavigationBarItem(
                    selected = true, // <-- Profile activo
                    onClick = { },
                    icon = { /* add icon if you want */ },
                    label = { Text("Profile") }
                )
            }
        }
    ) { inner ->
        Column(
            Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(96.dp).clip(CircleShape)
            )
            Spacer(Modifier.height(8.dp))
            Text(fullName.ifBlank { "invitado" }, style = MaterialTheme.typography.titleLarge)

            Spacer(Modifier.height(24.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Carné:")
                Text(carnet)
            }

            Spacer(Modifier.height(32.dp))
            Button(
                onClick = {
                    scope.launch {
                        prefs.clearUserName()
                        onLogout()
                    }
                },
                shape = MaterialTheme.shapes.large
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}
