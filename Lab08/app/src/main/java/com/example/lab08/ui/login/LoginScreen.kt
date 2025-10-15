package com.example.lab08.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab08.R
import com.example.lab08.data.CharacterDb
import com.example.lab08.data.LocationDb
import com.example.lab08.data.local.toEntities
import com.example.lab08.data.repository.ServiceLocator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoggedIn: () -> Unit) {
    val context = LocalContext.current
    val vm = remember { LoginViewModel(ServiceLocator, context) }
    var name by remember { mutableStateOf(TextFieldValue("")) }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.rick_morty_logo),
                contentDescription = null,
                modifier = Modifier.size(140.dp)
            )
            Spacer(Modifier.height(16.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Tu nombre") },
                singleLine = true,
                enabled = !vm.isLoading
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = { vm.loginAndSync(name.text) { onLoggedIn() } },
                enabled = name.text.isNotBlank() && !vm.isLoading
            ) {
                if (vm.isLoading)
                    CircularProgressIndicator(Modifier.size(18.dp), strokeWidth = 2.dp)
                else
                    Text("Iniciar sesión")
            }
        }
    }
}

class LoginViewModel(
    private val sl: ServiceLocator,
    private val context: android.content.Context
) : ViewModel() {
    var isLoading by mutableStateOf(false); private set

    fun loginAndSync(name: String, onDone: () -> Unit) = viewModelScope.launch {
        isLoading = true

        // 1) delay visible de 4s
        delay(4000)

        // 2) sincronización inicial desde tus DB locales hacia Room
        val characters = CharacterDb().getAllCharacters().toEntities()
        val locations  = LocationDb().getAllLocations().toEntities()
        sl.provideCharactersRepo(context).initialSync(characters)
        sl.provideLocationsRepo(context).initialSync(locations)

        // 3) guardar nombre en DataStore
        sl.provideUserPrefs(context).setUserName(name)

        isLoading = false
        onDone()
    }
}
