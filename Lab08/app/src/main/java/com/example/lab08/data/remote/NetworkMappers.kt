package com.example.lab08.data.remote

import com.example.lab08.data.remote.dto.CharacterDto
import com.example.lab08.data.remote.dto.LocationDto
import com.example.lab08.data.room.entities.CharacterEntity
import com.example.lab08.data.room.entities.LocationEntity

@JvmName("characterDtosToEntities")
fun List<CharacterDto>.toEntities(): List<CharacterEntity> = map {
    CharacterEntity(
        id = it.id,
        name = it.name,
        species = it.species,
        status = it.status,
        gender = it.gender,
        imageUrl = it.imageUrl
    )
}

@JvmName("locationDtosToEntities")
fun List<LocationDto>.toEntities(): List<LocationEntity> = map {
    LocationEntity(
        id = it.id,
        name = it.name,
        type = it.type,
        dimension = it.dimension
    )
}
