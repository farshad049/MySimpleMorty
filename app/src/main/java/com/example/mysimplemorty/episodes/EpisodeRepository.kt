package com.example.mysimplemorty.episodes

import com.example.mysimplemorty.network.NetworkLayer
import com.example.mysimplemorty.network.responseModel.GetEpisodePageResponse

class EpisodeRepository {
    suspend fun fetchEpisodePage(pageIndex:Int):GetEpisodePageResponse?{
        val request=NetworkLayer.apiClient.getEpisodePage(pageIndex)
        if (!request.isSuccessful){
            return null
        }
        return request.body
    }
}