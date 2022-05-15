package com.example.mysimplemorty.characters

import com.example.mysimplemorty.network.NetworkLayer
import com.example.mysimplemorty.network.responseModel.GetCharactersPageResponse

class CharactersRepository {
    suspend fun getCharactersPage(pageIndex:Int):GetCharactersPageResponse?{
        val request=NetworkLayer.apiClient.getCharactersPage(pageIndex)

        if (request.failed || !request.isSuccessful){
            return null
        }
        return request.body
    }
}