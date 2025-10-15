@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.lab08.ui.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lab08.data.room.entities.LocationEntity
import com.example.lab08.ui.common.Error
import com.example.lab08.ui.common.Loading

@Composable
fun LocationsScreen(
    onLocationClick: (Int) -> Unit,
    onCharactersClick: () -> Unit,  // (se usan en la BottomBar global)
    onProfileClick: () -> Unit      // (se usan en la BottomBar global)
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val vm = remember { LocationsViewModel(context.applicationContext as android.app.Application) }
    val s by vm.state.collectAsState()

    when {
        s.isLoading -> Loading()
        s.hasError  -> Error("Error cargando locations", onRetry = { vm.reload() })
        else -> LazyColumn {
            items(items = s.data, key = { it.id }) { l: LocationEntity ->
                ListItem(
                    headlineContent   = { Text(l.name, style = MaterialTheme.typography.titleMedium) },
                    supportingContent = { Text(l.type) },
                    modifier = Modifier
                        .clickable { onLocationClick(l.id) }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                )
                Divider()
            }
        }
    }
}
