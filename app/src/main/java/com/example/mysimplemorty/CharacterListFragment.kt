package com.example.mysimplemorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.mysimplemorty.characters.CharactersViewModel
import com.example.mysimplemorty.databinding.FragmentCharacterListBinding

class CharacterListFragment: Fragment() {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
    }
    private val epoxyController= CharacterListEpoxyController(::onCharacterClick)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.charactersPageListLiveData.observe(viewLifecycleOwner){
            epoxyController.submitList(it)
        }

        binding.epoxyRecyclerView.setController(epoxyController)








    }//FUN
    private fun onCharacterClick(characterId:Int) {
        val directions=CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(characterId)
        findNavController().navigate(directions)
    }















    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}