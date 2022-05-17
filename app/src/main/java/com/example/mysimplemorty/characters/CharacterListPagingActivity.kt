package com.example.mysimplemorty.characters

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mysimplemorty.CharacterDetailActivity
import com.example.mysimplemorty.databinding.ActivityCharacterListBinding

class CharacterListPagingActivity : AppCompatActivity() {
    private var _binding: ActivityCharacterListBinding? = null
    private val binding get() = _binding!!
    private val epoxyController=CharacterListPagingEpoxyController(::onCharacterClick)



    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.charactersPageListLiveData.observe(this){
            epoxyController.submitList(it)
        }

        binding.epoxyRecyclerView.setController(epoxyController)













    }//FUN

    private fun onCharacterClick(characterId: Int) {
        val intent=Intent(this,CharacterDetailActivity::class.java)
        intent.putExtra("characterId",characterId)
        startActivity(intent)
    }















    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}