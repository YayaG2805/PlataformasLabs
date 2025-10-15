@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lab08.ui.common.Error
import com.example.lab08.ui.common.Loading

@Composable
fun LocationDetailScreen(id: Int, onBack: () -> Unit) {
    val context = LocalContext.current
    val vm = remember { LocationDetailViewModel(context.applicationContext as android.app.Application, id) }
    val s by vm.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Location details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { inner ->
        when {
            s.isLoading -> Loading(Modifier.padding(inner))
            s.hasError || s.data == null -> Error("No encontrado", onRetry = { vm.reload() }, modifier = Modifier.padding(inner))
            else -> {
                val l = s.data!!
                Column(
                    modifier = Modifier
                        .padding(inner)
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("#${l.id} ${l.name}", style = MaterialTheme.typography.headlineSmall)
                    Spacer(Modifier.height(16.dp))
                    AttributeRow("Type:",      l.type)
                    AttributeRow("Dimension:", l.dimension)
                }
            }
        }
    }
}

@Composable
private fun AttributeRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}
