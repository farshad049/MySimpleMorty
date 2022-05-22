package com.example.mysimplemorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysimplemorty.domain.models.Character
import com.example.mysimplemorty.network.NetworkCache
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import kotlinx.coroutines.launch

//this is for characterDetailActivity
class SharedViewModel:ViewModel() {
    private val repository=SharedRepository()
    private val _characterByIdLiveData=MutableLiveData<Character?>()
            val characterByIdLiveData:LiveData<Character?> =_characterByIdLiveData

//    fun fetchCharacter(characterId:Int){
//
//        //if the character is already cached , so don't need to fetch it again
//        val cachedCharacter=NetworkCache.characterMap[characterId]
//        if (cachedCharacter != null){
//            _characterByIdLiveData.postValue(cachedCharacter)
//            //remove this when the data is updating quickly
//            return
//        }
//
//        //otherwise we need to make the network call
//        viewModelScope.launch {
//           val response = repository.getCharacterById(characterId)
//            _characterByIdLiveData.postValue(response)
//
//            //check if response is not null put in in cache
//            response?.let {
//                NetworkCache.characterMap[characterId]=it
//            }
//        }
//    }

    fun fetchCharacter(characterId:Int){
        viewModelScope.launch {
            val response=repository.getCharacterById(characterId)
            _characterByIdLiveData.postValue(response)
        }
    }


}