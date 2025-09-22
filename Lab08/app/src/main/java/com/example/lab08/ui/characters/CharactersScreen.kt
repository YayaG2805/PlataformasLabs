@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.lab08.data.Character
import com.example.lab08.ui.common.ErrorView
import com.example.lab08.ui.common.LoadingView

@Composable
fun CharactersScreen(
    onCharacterClick: (Int) -> Unit,
    vm: CharactersViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    Surface(Modifier.fillMaxSize()) {
        Column {
            TopAppBar(title = { Text("Personajes") })

            when {
                state.isLoading -> LoadingView("Cargando")
                state.hasError  -> ErrorView(
                    "Error al obtener listado de personajes. Intenta de nuevo."
                ) { vm.load() }

                else -> {
                    LazyColumn(Modifier.fillMaxSize()) {
                        items(state.data) { ch ->
                            CharacterRow(ch) { onCharacterClick(ch.id) }
                            Divider()
                        }
                    }
                }
            }
        }
    }
}

@Composable private fun CharacterRow(ch: Character, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(ch.name, fontWeight = FontWeight.SemiBold) },
        supportingContent = { Text("${ch.species} • ${ch.status}") },
        leadingContent = {
            SubcomposeAsyncImage(model = ch.image, contentDescription = ch.name, modifier = Modifier.size(48.dp)) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading -> CircularProgressIndicator(Modifier.size(16.dp))
                    is AsyncImagePainter.State.Error -> Icon(Icons.Default.Person, contentDescription = null)
                    else -> SubcomposeAsyncImageContent()
                }
            }
        },
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}
