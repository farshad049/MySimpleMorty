package com.example.mysimplemorty

class SharedRepository {
    suspend fun getCharacterById(characterId:Int): GetCharacterByIDResponse? {
        val request=NetworkLayer.apiClient.getCharacterById(characterId)
        //if request failed because of network issues
        if (request.failed){
            return null
        }
        //it happens after network level, check for fetching data by retrofit was ok or not
        if (!request.isSuccessful){
            return null
        }

        return request.body

    }
}