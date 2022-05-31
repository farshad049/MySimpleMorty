package com.example.mysimplemorty.characters.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mysimplemorty.domain.mappers.CharacterMapper
import com.example.mysimplemorty.domain.models.Character
import com.example.mysimplemorty.network.NetworkLayer

class CharacterSearchPagingSource(
    private val userSearch:String,
    private val localExceptionCallBack: (LocalException)->Unit
): PagingSource<Int, Character>() {

    sealed class LocalException(val title:String, val description:String=""):Exception(){
        object EmptySearch :LocalException(title = "Start Typing To Search")
        object NoResult:LocalException(title = "whoops", description = "looks like your search didn't return any result")
    }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {

        //if user didn't type anything yet, do this exception
        if (userSearch.isEmpty()) {
            val exception = LocalException.EmptySearch
            localExceptionCallBack(exception)
            return LoadResult.Error(exception)
        }

        val pageNumber = params.key ?: 1
        val previewPage= if (pageNumber ==1 ) null else pageNumber -1
        val request= NetworkLayer.apiClient.getCharactersPageByName(characterName = userSearch, pageIndex = pageNumber)

        //if there were no result for search, do this exception, this is handling by the backend, it has been set to answer back code 404 when request was not found
        if (request.data?.code()==404) {
            val exception = LocalException.NoResult
            localExceptionCallBack(exception)
            return LoadResult.Error(exception)
        }

        //when !pageRequest.isSuccessful do this
        request.exception?.let {
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            //we map it because the parent function has to return Character
            data = request.bodyNullable?.results?.map { CharacterMapper.buildFrom(it) } ?: emptyList(),
            prevKey = previewPage,
            nextKey = getPageIndexFromNext(request.bodyNullable?.info?.next)
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


// take page number from this "https://rickandmortyapi.com/api/character/?page=2&name=rick&status=alive"
    private fun getPageIndexFromNext(next:String?):Int?{
        if (next == null) {
            return null
        }

        val remainder = next.substringAfter("page=") //would be something like "2&name=rick&status=alive"
        val finalIndex = if (remainder.contains('&')) {
            remainder.indexOfFirst { it == '&' }
        } else {
            remainder.length
        }

        return remainder.substring(0, finalIndex).toIntOrNull()
    }

}