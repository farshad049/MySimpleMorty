package com.example.mysimplemorty.characters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mysimplemorty.R
import com.example.mysimplemorty.SharedViewModel
import com.example.mysimplemorty.databinding.ActivityCharacterListBinding

class CharacterListPagingActivity : AppCompatActivity() {
    private var _binding: ActivityCharacterListBinding? = null
    private val binding get() = _binding!!
    private val epoxyController=CharacterListPagingEpoxyController()
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















    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}