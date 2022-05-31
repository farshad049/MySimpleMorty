package com.example.mysimplemorty.characters.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.dmp.simplemorty.arch.Event

class CharacterSearchViewModel:ViewModel() {




    private var currentUserSearch:String=""
    //every time pagingSource be called, this block of code will be run because every time user may type a new string to be searched
    private var pagingSource:CharacterSearchPagingSource? =null
    get() {
        if (field == null || field?.invalid == true){
            field = CharacterSearchPagingSource(currentUserSearch){
                // Notify our LiveData of an issue from the PagingSource
                _localExceptionEventLiveData.postValue(Event(it))
            }
        }
        return field
    }




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
        //that would be like pagingFactory, we do this instead of using the paging source directly, because it has to be fetched every time user types a new string
        pagingSource!!
    }.flow.cachedIn(viewModelScope)


    // For error handling propagation
    //Event set the flag to be null or opposite
    private val _localExceptionEventLiveData = MutableLiveData<Event<CharacterSearchPagingSource.LocalException>>()
    val localExceptionEventLiveData: LiveData<Event<CharacterSearchPagingSource.LocalException>> = _localExceptionEventLiveData


    fun submitQuery(userSearch:String){
        currentUserSearch=userSearch
        pagingSource?.invalidate()

    }
}