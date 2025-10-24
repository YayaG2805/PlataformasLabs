package com.example.lab08.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lab08.data.room.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<LocationEntity>)
    @Query("SELECT * FROM locations")
    fun getAll(): Flow<List<LocationEntity>>
    @Query("SELECT * FROM locations WHERE id = :id")
    fun getById(id: Int): Flow<LocationEntity?>

    @Query("SELECT COUNT(*) FROM locations")
    suspend fun count(): Int
}
