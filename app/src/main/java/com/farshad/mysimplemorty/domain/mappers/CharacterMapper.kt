package com.farshad.mysimplemorty.domain.mappers

import com.farshad.mysimplemorty.domain.models.Character
import com.farshad.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import com.farshad.mysimplemorty.network.responseModel.GetEpisodeByIdResponse
import kotlin.system.exitProcess

object CharacterMapper {

    fun buildFrom(
        response: GetCharacterByIdResponse,
        episodes: List<GetEpisodeByIdResponse> = emptyList()
    ): Character {
        return Character(
            episodeList = episodes.map {
                EpisodeMapper.buildFrom(it)
            },
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(response.location.name,response.location.url),
            name = response.name,
            origin = Character.Origin(response.origin.name,response.origin.url),
            species = response.species,
            status = response.status

        )
    }
}