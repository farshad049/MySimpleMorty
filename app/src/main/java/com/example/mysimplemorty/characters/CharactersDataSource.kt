package com.example.mysimplemorty.characters

import androidx.paging.PageKeyedDataSource
import com.example.mysimplemorty.network.NetworkLayer
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//take care of paging then we hav to pass it to dataSourceFactory
//this will be used by DataSourceFactory

class CharactersDataSource(
    private val coroutineScope:CoroutineScope,
    private val repository: CharacterRepository
    )
    :PageKeyedDataSource<Int,GetCharacterByIdResponse>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, GetCharacterByIdResponse>
    ) {
        coroutineScope.launch {
            val page=repository.getCharactersPage(1)

            if (page == null){
                callback.onResult(emptyList(),null,null)
                return@launch
            }
            //if result is not empty fetch the character list of that particular page
            callback.onResult(page.results,null,getPageIndexFromNext(page.info.next))//we are using getPageIndexFromNext to take the next page index,because rickandmortu API has split list to pages already

        }
    }


    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {

        coroutineScope.launch {
            //params is the parameter that has been passed to LoadAfter from LoadInitial and its also the current page index
            val page=repository.getCharactersPage(params.key)
            if (page == null){
                callback.onResult(emptyList(),null)
                return@launch
            }

            callback.onResult(page.results,params.key + 1 )
        }
    }


    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, GetCharacterByIdResponse>
    ) {
        //nothing to do
    }


    //split "?page=2" from the end of "https://rickandmortyapi.com/api/character/?page=2" and take the page number, in order to use it as next page String address
    private fun getPageIndexFromNext(next:String?):Int?{
        //split on "?page=" and take the second part(which is the number at the end)
        return next?.split("?page=")?.get(1)?.toInt()
    }


}