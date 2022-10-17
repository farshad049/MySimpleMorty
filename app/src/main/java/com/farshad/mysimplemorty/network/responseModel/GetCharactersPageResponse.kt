package com.farshad.mysimplemorty.network.responseModel

//based on API model
data class GetCharactersPageResponse(
    //paging data
    val info:PageInfo=PageInfo(),
    //characters data
    val results: List<GetCharacterByIdResponse> = emptyList()
)