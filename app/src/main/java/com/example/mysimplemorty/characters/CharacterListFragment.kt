package com.example.mysimplemorty.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mysimplemorty.databinding.FragmentCharacterListBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterListFragment: Fragment() {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    //region we can either do this or that, but second method doesn't work on Activities but only on fragments
//    private val viewModel: CharactersViewModel by lazy {
//        ViewModelProvider(this).get(CharactersViewModel::class.java)
//    }
    //endregion
    private val viewModel: CharactersViewModel by viewModels()

    private val epoxyController= CharacterListEpoxyController(::onCharacterClick)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel.charactersPageListLiveData.observe(viewLifecycleOwner){
//            epoxyController.submitList(it)
//        }

        //flow collect needs to run inside coroutine
        lifecycleScope.launch {
            viewModel.flow.collectLatest {
                epoxyController.submitData(it)
            }
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