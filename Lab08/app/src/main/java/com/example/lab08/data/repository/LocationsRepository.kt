package com.example.lab08.data.repository

import com.example.lab08.data.room.dao.LocationDao
import com.example.lab08.data.room.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

class LocationsRepository(private val dao: LocationDao) {
    fun getAll(): Flow<List<LocationEntity>> = dao.getAll()
    fun getById(id: Int): Flow<LocationEntity?> = dao.getById(id)
    suspend fun initialSync(list: List<LocationEntity>) = dao.insertAll(list)
}
