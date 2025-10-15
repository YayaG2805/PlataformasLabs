package com.example.lab08.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val species: String,
    val status: String,
    val gender: String,
    val imageUrl: String
)
