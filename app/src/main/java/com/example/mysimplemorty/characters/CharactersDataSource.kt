package com.example.mysimplemorty.characters

import androidx.lifecycle.ViewModel
import androidx.paging.PageKeyedDataSource
import com.example.mysimplemorty.SharedViewModel
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharactersDataSource(
    private val coroutineScope:CoroutineScope,
    private val repository:CharactersRepository
    )
    :PageKeyedDataSource<Int,GetCharacterByIdResponse>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val characterList=repository.getCharacterList(1)
            callback.onResult(characterList,null,1)
        }
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val characterList=repository.getCharacterList(params.key)
            callback.onResult(characterList,params.key + 1 )
        }
    }

    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        //nothing to do
    }


}