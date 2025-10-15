package com.example.lab08.data.local

import com.example.lab08.data.Character
import com.example.lab08.data.Location
import com.example.lab08.data.room.entities.CharacterEntity
import com.example.lab08.data.room.entities.LocationEntity

@JvmName("charactersToEntities")
fun List<Character>.toEntities(): List<CharacterEntity> =
    map {
        CharacterEntity(
            id = it.id,
            name = it.name,
            species = it.species,
            status = it.status,
            gender = it.gender,
            imageUrl = it.image
        )
    }

@JvmName("locationsToEntities")
fun List<Location>.toEntities(): List<LocationEntity> =
    map {
        LocationEntity(
            id = it.id,
            name = it.name,
            type = it.type,
            dimension = it.dimension
        )
    }
