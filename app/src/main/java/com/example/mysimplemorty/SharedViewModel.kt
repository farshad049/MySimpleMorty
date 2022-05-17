package com.example.mysimplemorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysimplemorty.domain.models.Character
import com.example.mysimplemorty.network.responseModel.GetCharacterByIdResponse
import kotlinx.coroutines.launch

//this is for characterDetailActivity
class SharedViewModel:ViewModel() {
    private val repository=SharedRepository()
    private val _characterByIdLiveData=MutableLiveData<Character?>()
            val characterByIdLiveData:LiveData<Character?> =_characterByIdLiveData

    fun refreshCharacter(characterId:Int){
        viewModelScope.launch {
           val response= repository.getCharacterById(characterId)
            _characterByIdLiveData.postValue(response)
        }
    }


}