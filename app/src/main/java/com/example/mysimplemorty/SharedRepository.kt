package com.example.mysimplemorty

class SharedRepository {
    suspend fun getCharacterById(characterId:Int): GetCharacterByIDResponse? {
        val request=NetworkLayer.apiClient.getCharacterByID(characterId)
        return if (request.isSuccessful){
            request.body()!!
        }else{
            null
        }
    }
}