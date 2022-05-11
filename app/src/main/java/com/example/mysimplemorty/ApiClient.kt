package com.example.mysimplemorty

import retrofit2.Response

class ApiClient(private val rickAndMortyService: RickAndMortyService){
    suspend fun getCharacterByID(characterId:Int):Response<GetCharacterByIDResponse>{
        return rickAndMortyService.getCharacterById(characterId)
    }
}