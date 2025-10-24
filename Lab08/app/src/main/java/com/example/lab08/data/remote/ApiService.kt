package com.example.lab08.data.remote

import com.example.lab08.data.remote.dto.CharactersResponseDto
import com.example.lab08.data.remote.dto.LocationsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int = 1): CharactersResponseDto

    @GET("location")
    suspend fun getLocations(@Query("page") page: Int = 1): LocationsResponseDto
}
