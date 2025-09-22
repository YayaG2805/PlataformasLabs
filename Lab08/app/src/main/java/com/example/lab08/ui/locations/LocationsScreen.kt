@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
fun LocationsScreen(
    onLocationClick: (Int) -> Unit,
    vm: LocationsViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    Surface(Modifier.fillMaxSize()) {
        Column {
            TopAppBar(title = { Text("Ubicaciones") })

            when {
                state.isLoading -> LoadingView("Cargando")
                state.hasError  -> ErrorView("Error al obtener ubicaciones. Intenta de nuevo.") { vm.load() }
                else -> {
                    LazyColumn(Modifier.fillMaxSize()) {
                        items(state.data) { loc ->
                            ListItem(
                                headlineContent = { Text(loc.name) },
                                supportingContent = { Text("${loc.type} • ${loc.dimension}") },
                                modifier = Modifier
                                    .clickable { onLocationClick(loc.id) }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                            Divider()
                        }
                    }
                }
            }
        }
    }
}
