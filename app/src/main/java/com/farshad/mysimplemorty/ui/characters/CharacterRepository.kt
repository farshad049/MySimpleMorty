package com.farshad.mysimplemorty.ui.characters

import com.farshad.mysimplemorty.domain.mappers.CharacterMapper
import com.farshad.mysimplemorty.domain.models.Character
import com.farshad.mysimplemorty.network.NetworkCache
import com.farshad.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.farshad.mysimplemorty.network.NetworkLayer
import com.farshad.mysimplemorty.network.responseModel.GetCharactersPageResponse
import com.farshad.mysimplemorty.network.responseModel.GetEpisodeByIdResponse

class CharacterRepository {

    //take the pages for paging
    suspend fun getCharactersPage(pageIndex:Int): GetCharactersPageResponse?{
        val request=NetworkLayer.apiClient.getCharactersPage(pageIndex)

        if (request.failed || !request.isSuccessful){
            return null
        }
        return request.body
    }


    suspend fun getCharacterById(characterId:Int): Character? {
        //handle the cache
       val cachedCharacter= NetworkCache.characterMap[characterId]
       if (cachedCharacter != null){
           return cachedCharacter
       }

        val request= NetworkLayer.apiClient.getCharacterById(characterId)
        //if request failed because of network issues
        if (request.failed || !request.isSuccessful) {
            return null
        }

        val networkEpisode = getEpisodesFromCharacterResponse(request.body)
        val character=CharacterMapper.buildFrom(response = request.body,episodes=networkEpisode)

        //update the cache state
        NetworkCache.characterMap[characterId]=character
        return character
    }




    private suspend fun getEpisodesFromCharacterResponse(characterResponse:GetCharacterByIdResponse)
    : List<GetEpisodeByIdResponse> {

        //take the episode number from the end of link of each episode and make a list of it
        //we use map to modify the list and extend it to a new format list
        //this return something like this "[1,2,3]"
        val episodeRange=characterResponse.episode.map {
            it.substring(it.lastIndexOf("/")+1)
        }

        val request=NetworkLayer.apiClient.getMultipleEpisode(episodeRange)

        if (request.failed || !request.isSuccessful) {
            return emptyList()
        }
        return request.body
    }
}