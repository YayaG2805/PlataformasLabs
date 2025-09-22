@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lab08.ui.common.ErrorView
import com.example.lab08.ui.common.LoadingView

@Composable
fun LocationDetailScreen(
    onBack: () -> Unit,
    vm: LocationDetailViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    Surface(Modifier.fillMaxSize()) {
        Column {
            TopAppBar(
                title = { Text("Detalle de ubicación") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )

            when {
                state.isLoading -> LoadingView("Cargando")
                state.hasError  -> ErrorView("No se pudo cargar la ubicación.", onRetry = { vm.load() })
                else -> {
                    val loc = state.data!!
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(loc.name, style = MaterialTheme.typography.headlineSmall)
                        Text("Tipo: ${loc.type}")
                        Text("Dimensión: ${loc.dimension}")
                    }
                }
            }
        }
    }
}
