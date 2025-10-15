package com.example.lab08.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lab08.data.room.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CharacterEntity>)
    @Query("SELECT * FROM characters")
    fun getAll(): Flow<List<CharacterEntity>>
    @Query("SELECT * FROM characters WHERE id = :id")
    fun getById(id: Int): Flow<CharacterEntity?>
}
