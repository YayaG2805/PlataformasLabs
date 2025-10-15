package com.example.lab08.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

private const val DS_NAME = "user_prefs"
val Context.dataStore by preferencesDataStore(name = DS_NAME)

class UserPrefsRepository(private val context: Context) {
    private object Keys { val NAME = stringPreferencesKey("name") }

    val userName: Flow<String?> = context.dataStore.data
        .catch { e -> if (e is IOException) emit(emptyPreferences()) else throw e }
        .map { it[Keys.NAME] }

    suspend fun setUserName(name: String) = context.dataStore.edit { it[Keys.NAME] = name }
    suspend fun clearUserName()           = context.dataStore.edit { it.remove(Keys.NAME) }
}
