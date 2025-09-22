@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab08.data.LocationDb

@Composable
fun LocationDetailScreen(id: Int, onBack: () -> Unit) {
    val db = remember { LocationDb() }
    val loc = remember(id) { db.getLocationById(id) }

    Surface(Modifier.fillMaxSize()) {
        Column {
            TopAppBar(
                title = { Text("Location details") },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Filled.ArrowBack, "Back") }
                }
            )
            Column(Modifier.fillMaxWidth().padding(24.dp)) {
                Text(loc.name, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(16.dp))
                RowLine("ID:", loc.id.toString())
                RowLine("Type:", loc.type)
                RowLine("Dimension:", loc.dimension)
            }
        }
    }
}

@Composable private fun RowLine(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 6.dp)) {
        Text(label, modifier = Modifier.weight(1f))
        Text(value, modifier = Modifier.weight(1f))
    }
}
