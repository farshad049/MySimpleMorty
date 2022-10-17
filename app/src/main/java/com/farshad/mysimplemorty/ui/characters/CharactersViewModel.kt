package com.farshad.mysimplemorty.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CharactersViewModel @Inject constructor():ViewModel() {



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