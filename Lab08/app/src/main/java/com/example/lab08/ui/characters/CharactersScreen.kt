@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.characters

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.lab08.ui.common.Error
import com.example.lab08.ui.common.Loading
import com.example.lab08.data.room.entities.CharacterEntity

@Composable
fun CharactersScreen(
    onCharacterClick: (Int) -> Unit,
    onLocationsClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val context = LocalContext.current
    val vm = remember { CharactersViewModel(context.applicationContext as android.app.Application) }
    val s by vm.state.collectAsState()

    BackHandler { (context as? Activity)?.finish() }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(selected = true, onClick = {}, label = { Text("Characters") }, icon = { })
                NavigationBarItem(selected = false, onClick = onLocationsClick, label = { Text("Locations") }, icon = { })
                NavigationBarItem(selected = false, onClick = onProfileClick, label = { Text("Profile") }, icon = { })
            }
        }
    ) { inner ->
        when {
            s.isLoading -> Loading(Modifier.padding(inner))
            s.hasError  -> Error("Error cargando personajes", onRetry = { vm.reload() }, modifier = Modifier.padding(inner))
            else -> LazyColumn(Modifier.padding(inner)) {
                items(items = s.data, key = { it.id }) { ch: CharacterEntity ->
                    ListItem(
                        headlineContent   = { Text(ch.name) },
                        supportingContent = { Text("${ch.species} • ${ch.status}") },
                        leadingContent = {
                            Image(
                                painter = rememberAsyncImagePainter(ch.imageUrl),
                                contentDescription = ch.name,
                                modifier = Modifier.size(48.dp)
                            )
                        },
                        modifier = Modifier.clickable { onCharacterClick(ch.id) }
                    )
                    Divider()
                }
            }
        }
    }
}
