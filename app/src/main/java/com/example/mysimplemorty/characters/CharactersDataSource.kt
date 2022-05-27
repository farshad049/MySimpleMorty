package com.example.mysimplemorty.characters

import androidx.paging.PageKeyedDataSource
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mysimplemorty.domain.mappers.CharacterMapper
import com.example.mysimplemorty.domain.mappers.EpisodeMapper
import com.example.mysimplemorty.domain.models.Character
import com.example.mysimplemorty.domain.models.Episode
import com.example.mysimplemorty.network.NetworkLayer
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class CharacterDatasource(

): PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        val pageNumber = params.key ?: 1
        val previewPage= if (pageNumber ==1 ) null else pageNumber -1
        val pageRequest=NetworkLayer.apiClient.getCharactersPage(pageNumber)

        //when !pageRequest.isSuccessful do this
        pageRequest.exception?.let {
            return LoadResult.Error(it)
        }


        return LoadResult.Page(
            //we map it because the parent function has to return Character
            data = pageRequest.body.results.map { CharacterMapper.buildFrom(it) },
            prevKey = previewPage,
            nextKey = getPageIndexFromNext(pageRequest.body.info.next)
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }



//region paging2
////take care of paging then we hav to pass it to dataSourceFactory
////this will be used by DataSourceFactory
//
//class CharactersDataSource(
//    private val coroutineScope:CoroutineScope,
//    private val repository: CharacterRepository
//    )
//    :PageKeyedDataSource<Int,GetCharacterByIdResponse>() {
//    override fun loadInitial(
//        params: LoadInitialParams<Int>,
//        callback: LoadInitialCallback<Int, GetCharacterByIdResponse>
//    ) {
//        coroutineScope.launch {
//            val page=repository.getCharactersPage(1)
//
//            if (page == null){
//                callback.onResult(emptyList(),null,null)
//                return@launch
//            }
//            //if result is not empty fetch the character list of that particular page
//            callback.onResult(page.results,null,getPageIndexFromNext(page.info.next))//we are using getPageIndexFromNext to take the next page index,because rickandmortu API has split list to pages already
//
//        }
//    }
//
//
//    override fun loadAfter(
//        params: LoadParams<Int>,
//        callback: LoadCallback<Int, GetCharacterByIdResponse>
//    ) {
//
//        coroutineScope.launch {
//            //params is the parameter that has been passed to LoadAfter from LoadInitial and its also the current page index
//            val page=repository.getCharactersPage(params.key)
//            if (page == null){
//                callback.onResult(emptyList(),null)
//                return@launch
//            }
//
//            callback.onResult(page.results,params.key + 1 )
//        }
//    }
//
//
//    override fun loadBefore(
//        params: LoadParams<Int>,
//        callback: LoadCallback<Int, GetCharacterByIdResponse>
//    ) {
//        //nothing to do
//    }
    //endregion paging2


    //split "?page=2" from the end of "https://rickandmortyapi.com/api/character/?page=2" and take the page number, in order to use it as next page String address
    private fun getPageIndexFromNext(next:String?):Int?{
        //split on "?page=" and take the second part(which is the number at the end)
        return next?.split("?page=")?.get(1)?.toInt()
    }


}