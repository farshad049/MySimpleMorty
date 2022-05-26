package com.example.mysimplemorty.episodes.episodeDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysimplemorty.domain.models.Episode
import com.example.mysimplemorty.episodes.EpisodeRepository
import kotlinx.coroutines.launch

class EpisodeDetailViewModel:ViewModel (){
    private val repository= EpisodeRepository()
    private val _episodeByIdLiveData= MutableLiveData<Episode?>()
    val episodeByIdLiveData: LiveData<Episode?> =_episodeByIdLiveData


    fun fetchEpisode(episodeId:Int){
        viewModelScope.launch {
            val response=repository.getEpisodeById(episodeId)
            _episodeByIdLiveData.postValue(response)
        }
    }
}