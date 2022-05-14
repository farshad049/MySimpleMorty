package com.example.mysimplemorty

import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.example.mysimplemorty.network.NetworkLayer

class SharedRepository {
    suspend fun getCharacterById(characterId:Int): GetCharacterByIdResponse? {
        val request= NetworkLayer.apiClient.getCharacterById(characterId)
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