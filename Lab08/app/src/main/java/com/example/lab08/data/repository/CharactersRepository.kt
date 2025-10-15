package com.example.lab08.data.repository

import com.example.lab08.data.room.dao.CharacterDao
import com.example.lab08.data.room.entities.CharacterEntity
import kotlinx.coroutines.flow.Flow

class CharactersRepository(private val dao: CharacterDao) {
    fun getAll(): Flow<List<CharacterEntity>> = dao.getAll()
    fun getById(id: Int): Flow<CharacterEntity?> = dao.getById(id)
    suspend fun initialSync(list: List<CharacterEntity>) = dao.insertAll(list)
}
