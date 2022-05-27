package com.example.mysimplemorty.characters

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.mysimplemorty.episodes.EpisodeDataSource
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse

class CharactersViewModel:ViewModel() {
//region paging2
//    private val repository= CharacterRepository()
//
//    private val pageListConfig:PagedList.Config=PagedList.Config.Builder()
//        //Defines the number of items loaded at once from the PagingSource. we set it ot 20 because API give us up to 20 item in each page
//        .setPageSize(20)
//        .setPrefetchDistance(40)
//        .build()
//
//    private val dataSourceFactory = CharactersDataSourceFactory(viewModelScope,repository)
//
//    //preparing data for view layer which need dataSourceFactory and pageListConfig
//    val charactersPageListLiveData:LiveData<PagedList<GetCharacterByIdResponse>> =
//        LivePagedListBuilder(dataSourceFactory,pageListConfig).build()
   //endregion paging2

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(
            pageSize = 20,
            prefetchDistance = 40,
            //so we don't have to be worried about nulls in view layer
            enablePlaceholders = false
        )
    ) {
        CharacterDatasource()

    }.flow
        .cachedIn(viewModelScope)
}