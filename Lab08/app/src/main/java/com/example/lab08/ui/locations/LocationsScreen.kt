@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.lab08.data.Location
import com.example.lab08.data.LocationDb

@Composable
fun LocationsScreen(onLocationClick: (Int) -> Unit) {
    val db = remember { LocationDb() }
    val locations = remember { db.getAllLocations() }

    Surface(Modifier.fillMaxSize()) {
        Column {
            TopAppBar(title = { Text("Locations") })
            LazyColumn(Modifier.fillMaxSize()) {
                items(locations) { loc ->
                    LocationRow(loc) { onLocationClick(loc.id) }
                    Divider()
                }
            }
        }
    }
}

@Composable
private fun LocationRow(loc: Location, onClick: () -> Unit) {
    Column(Modifier.fillMaxWidth().clickable(onClick = onClick).padding(12.dp)) {
        Text(loc.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
        Text(loc.type, style = MaterialTheme.typography.bodyMedium)
    }
}
