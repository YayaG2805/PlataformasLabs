package com.example.lab08.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.lab08.data.room.dao.CharacterDao
import com.example.lab08.data.room.dao.LocationDao
import com.example.lab08.data.room.entities.CharacterEntity
import com.example.lab08.data.room.entities.LocationEntity

@Database(
    entities = [CharacterEntity::class, LocationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun locationDao(): LocationDao
}
