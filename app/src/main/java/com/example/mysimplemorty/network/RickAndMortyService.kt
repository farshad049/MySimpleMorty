package com.example.mysimplemorty.network

import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {

    @GET("character/{character-id}")
    suspend fun getCharacterById(@Path("character-id") CharacterId:Int): Response<GetCharacterByIdResponse>

}