package com.example.lab08.data.repository

import com.example.lab08.data.remote.ApiService
import com.example.lab08.data.remote.toEntities
import com.example.lab08.data.room.dao.CharacterDao
import com.example.lab08.data.room.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class CharactersRepository(
    private val dao: CharacterDao,
    private val api: ApiService
) {
    fun getAll(): Flow<List<CharacterEntity>> =
        dao.getAll().onStart {
            // OFFLINE FIRST: si no hay data, traemos del API (20 por defecto) y persistimos
            if (dao.count() == 0) {
                val page1 = api.getCharacters(page = 1)
                dao.insertAll(page1.results.toEntities())
            }
        }

    fun getById(id: Int): Flow<CharacterEntity?> = dao.getById(id)

    // usado por la sync manual del login (si la dejas) o tests
    suspend fun initialSync(list: List<CharacterEntity>) = dao.insertAll(list)
}
