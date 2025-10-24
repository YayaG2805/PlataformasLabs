package com.example.lab08.data.repository

import android.content.Context
import androidx.room.Room
import com.example.lab08.data.datastore.UserPrefsRepository
import com.example.lab08.data.remote.ApiService
import com.example.lab08.data.room.AppDatabase
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType

object ServiceLocator {
    @Volatile private var db: AppDatabase? = null
    @Volatile private var api: ApiService? = null

    private fun provideDatabase(context: Context): AppDatabase =
        db ?: synchronized(this) {
            db ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "rickmorty.db"
            ).build().also { db = it }
        }

    private fun provideApi(): ApiService =
        api ?: synchronized(this) {
            api ?: run {
                val logging = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
                val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()

                val json = Json { ignoreUnknownKeys = true }

                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .client(client)
                    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                    .build()

                retrofit.create(ApiService::class.java)
            }.also { api = it }
        }

    fun provideUserPrefs(context: Context)      = UserPrefsRepository(context.applicationContext)
    fun provideCharactersRepo(context: Context) =
        CharactersRepository(provideDatabase(context).characterDao(), provideApi())
    fun provideLocationsRepo(context: Context)  =
        LocationsRepository(provideDatabase(context).locationDao(), provideApi())
}
