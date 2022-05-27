package com.example.mysimplemorty.characters

import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope


// use DataSource to propagate the view layer, this will be used in ViewModel scope to make live data fot view layer
//class CharactersDataSourceFactory(
//    private val coroutineScope: CoroutineScope,
//    private val repository: CharacterRepository
//):androidx.paging.DataSource.Factory<Int,GetCharacterByIdResponse>() {
//
//    override fun create(): androidx.paging.DataSource<Int, GetCharacterByIdResponse> {
//        return CharactersDataSource(coroutineScope,repository)
//    }
//}