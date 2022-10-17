package com.farshad.mysimplemorty.network.responseModel

data class GetEpisodePageResponse(
    val info: PageInfo = PageInfo(),
    val results: List<GetEpisodeByIdResponse> = listOf()
)