package com.farshad.mysimplemorty.ui.episodes

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.farshad.mysimplemorty.domain.mappers.EpisodeMapper
import com.farshad.mysimplemorty.domain.models.Episode
import com.farshad.mysimplemorty.network.NetworkLayer

class EpisodeDataSource(

):PagingSource<Int,Episode>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {

        val pageNumber = params.key ?: 1
        val previewPage= if (pageNumber ==1 ) null else pageNumber -1
        val pageRequest=NetworkLayer.apiClient.getEpisodePage(pageNumber)

        //when !pageRequest.isSuccessful do this
        pageRequest.exception?.let {
            return LoadResult.Error(it)
        }

        return LoadResult.Page(
            //we map it because the parent function has to return Character
            data = pageRequest.bodyNullable?.results?.map { EpisodeMapper.buildFrom(it) } ?: emptyList(),
            prevKey = previewPage,
            nextKey = getPageIndexFromNext(pageRequest.bodyNullable?.info?.next)
        )
    }


    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
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


    //split "?page=2" from the end of "https://rickandmortyapi.com/api/character/?page=2" and take the page number, in order to use it as next page String address
    private fun getPageIndexFromNext(next:String?):Int?{
        //split on "?page=" and take the second part(which is the number at the end)
        return next?.split("?page=")?.get(1)?.toInt()
    }

}