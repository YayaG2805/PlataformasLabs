package com.example.lab08.data.repository

import android.content.Context
import androidx.room.Room
import com.example.lab08.data.datastore.UserPrefsRepository
import com.example.lab08.data.room.AppDatabase

object ServiceLocator {
    @Volatile private var db: AppDatabase? = null

    private fun provideDatabase(context: Context): AppDatabase =
        db ?: synchronized(this) {
            db ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "rickmorty.db"
            ).build().also { db = it }
        }

    fun provideUserPrefs(context: Context)      = UserPrefsRepository(context.applicationContext)
    fun provideCharactersRepo(context: Context) = CharactersRepository(provideDatabase(context).characterDao())
    fun provideLocationsRepo(context: Context)  = LocationsRepository(provideDatabase(context).locationDao())
}
