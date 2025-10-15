@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.characters

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.lab08.data.room.entities.CharacterEntity
import com.example.lab08.ui.common.Error
import com.example.lab08.ui.common.Loading

@Composable
fun CharactersScreen(
    onCharacterClick: (Int) -> Unit,
    onLocationsClick: () -> Unit,   // (se usan en la BottomBar global)
    onProfileClick: () -> Unit      // (se usan en la BottomBar global)
) {
    val context = LocalContext.current
    val vm = remember { CharactersViewModel(context.applicationContext as android.app.Application) }
    val s by vm.state.collectAsState()

    // En la lista, "back" cierra la app
    BackHandler { (context as? Activity)?.finish() }

    when {
        s.isLoading -> Loading()
        s.hasError  -> Error("Error cargando personajes", onRetry = { vm.reload() })
        else -> LazyColumn {
            items(items = s.data, key = { it.id }) { ch: CharacterEntity ->
                ListItem(
                    headlineContent   = { Text(ch.name, style = MaterialTheme.typography.titleMedium) },
                    supportingContent = { Text("${ch.species} • ${ch.status}") },
                    leadingContent    = {
                        Image(
                            painter = rememberAsyncImagePainter(ch.imageUrl),
                            contentDescription = ch.name,
                            modifier = Modifier.size(48.dp)
                        )
                    },
                    modifier = Modifier
                        .clickable { onCharacterClick(ch.id) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
                Divider()
            }
        }
    }
}
