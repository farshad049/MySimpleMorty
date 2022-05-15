package com.example.mysimplemorty.network.responseModel

//based on API model
data class GetCharactersPageResponse(
    //paging data
    val info: Info = Info(),
    //characters data
    val results: List<GetCharacterByIdResponse> = emptyList()
) {
    data class Info(
        val count: Int = 0,
        val pages: Int = 0,
        val next: String? = null,
        val prev: String? = null
    )
}