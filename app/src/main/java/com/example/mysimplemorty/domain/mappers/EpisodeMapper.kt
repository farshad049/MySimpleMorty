package com.example.mysimplemorty.domain.mappers

import com.example.mysimplemorty.domain.models.Episode
import com.example.mysimplemorty.network.responseModel.GetEpisodeByIdResponse

object EpisodeMapper {

    fun buildFrom(response: GetEpisodeByIdResponse):Episode{
        return Episode(
            id = response.id,
            name = response.name,
            airDate = response.air_date,
            episode = response.episode
        )
    }
}