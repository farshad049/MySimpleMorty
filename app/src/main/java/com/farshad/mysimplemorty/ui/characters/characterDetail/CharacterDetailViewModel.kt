package com.farshad.mysimplemorty.ui.characters.characterDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farshad.mysimplemorty.ui.characters.CharacterRepository
import com.farshad.mysimplemorty.domain.models.Character
import kotlinx.coroutines.launch

//this is for characterDetailActivity
class CharacterDetailViewModel:ViewModel() {
    private val repository= CharacterRepository()
    private val _characterByIdLiveData=MutableLiveData<Character?>()
            val characterByIdLiveData:LiveData<Character?> =_characterByIdLiveData


    fun fetchCharacter(characterId:Int){
        viewModelScope.launch {
            val response=repository.getCharacterById(characterId)
            _characterByIdLiveData.postValue(response)
        }
    }


}