@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.lab08.data.CharacterDb

@Composable
fun CharacterDetailScreen(id: Int, onBack: () -> Unit) {
    val db = remember { CharacterDb() }
    val ch = remember(id) { db.getCharacterById(id) }

    Surface(Modifier.fillMaxSize()) {
        Column {
            TopAppBar(
                title = { Text("Character details") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    model = ch.image,
                    contentDescription = ch.name,
                    modifier = Modifier.size(140.dp).clip(CircleShape),
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

                Spacer(Modifier.height(16.dp))
                Text(ch.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(24.dp))
                AttributeRow("Species:", ch.species)
                AttributeRow("Status:", ch.status)
                AttributeRow("Gender:", ch.gender)
            }
        }
    }
}

@Composable
private fun AttributeRow(label: String, value: String) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 6.dp)) {
        Text(label, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        Text(value, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
    }
}
