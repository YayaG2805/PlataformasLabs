@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.lab08.ui.common.Error
import com.example.lab08.ui.common.Loading

@Composable
fun LocationsScreen(
    onLocationClick: (Int) -> Unit,
    onCharactersClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val context = LocalContext.current
    val vm = remember { LocationsViewModel(context.applicationContext as android.app.Application) }
    val s by vm.state.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = false, onClick = onCharactersClick, label = { Text("Characters") }, icon = { })
                NavigationBarItem(selected = true,  onClick = {},               label = { Text("Locations")  }, icon = { })
                NavigationBarItem(selected = false, onClick = onProfileClick,   label = { Text("Profile")    }, icon = { })
            }
        }
    ) { inner ->
        when {
            s.isLoading -> Loading(Modifier.padding(inner))
            s.hasError  -> Error("Error cargando locations", onRetry = { vm.reload() }, modifier = Modifier.padding(inner))
            else -> LazyColumn(Modifier.padding(inner)) {
                items(items = s.data, key = { it.id }) { loc ->
                    ListItem(
                        headlineContent   = { Text(loc.name) },
                        supportingContent = { Text(loc.type) },
                        modifier = Modifier.clickable { onLocationClick(loc.id) }
                    )
                    Divider()
                }
            }
        }
    }
}
