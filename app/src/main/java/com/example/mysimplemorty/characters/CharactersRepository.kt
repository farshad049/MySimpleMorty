package com.example.mysimplemorty.characters

import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse

class CharactersRepository {
    suspend fun getCharacterList(pageIndex:Int):List<GetCharacterByIdResponse>{
        return emptyList()
    }
}