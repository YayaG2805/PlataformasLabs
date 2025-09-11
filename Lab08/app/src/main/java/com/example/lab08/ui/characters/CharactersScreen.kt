@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.lab08.data.Character
import com.example.lab08.data.CharacterDb

@Composable
fun CharactersScreen(onCharacterClick: (Int) -> Unit) {
    val db = remember { CharacterDb() }
    val characters = remember { db.getAllCharacters() }

    Surface(Modifier.fillMaxSize()) {
        Column {
            TopAppBar(title = { Text("Characters") })
            LazyColumn(Modifier.fillMaxSize()) {
                items(characters) { ch ->
                    CharacterRow(ch) { onCharacterClick(ch.id) }
                    Divider()
                }
            }
        }
    }
}

@Composable
private fun CharacterRow(ch: Character, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = ch.image,
            contentDescription = ch.name,
            modifier = Modifier.size(56.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                else -> Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                )
            }
        }

        Spacer(Modifier.width(12.dp))

        Column(Modifier.weight(1f)) {
            Text(ch.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Text("${ch.species} - ${ch.status}", style = MaterialTheme.typography.bodyMedium)
            Text(ch.gender, style = MaterialTheme.typography.bodySmall)
        }
    }
}
