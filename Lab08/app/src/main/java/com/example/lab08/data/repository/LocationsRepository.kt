package com.example.lab08.data.repository

import com.example.lab08.data.remote.ApiService
import com.example.lab08.data.remote.toEntities
import com.example.lab08.data.room.dao.LocationDao
import com.example.lab08.data.room.entities.LocationEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class LocationsRepository(
    private val dao: LocationDao,
    private val api: ApiService
) {
    fun getAll(): Flow<List<LocationEntity>> =
        dao.getAll().onStart {
            if (dao.count() == 0) {
                val page1 = api.getLocations(page = 1)
                dao.insertAll(page1.results.toEntities())
            }
        }

    fun getById(id: Int): Flow<LocationEntity?> = dao.getById(id)

    suspend fun initialSync(list: List<LocationEntity>) = dao.insertAll(list)
}
