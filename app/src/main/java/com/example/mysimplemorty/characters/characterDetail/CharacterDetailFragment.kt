package com.example.mysimplemorty.characters.characterDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mysimplemorty.BaseFragment
import com.example.mysimplemorty.NavGraphDirections
import com.example.mysimplemorty.R
import com.example.mysimplemorty.databinding.FragmentCharacterDetailBinding

class CharacterDetailFragment: BaseFragment(R.layout.fragment_character_detail) {
    private var _binding: FragmentCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CharacterDetailViewModel by lazy {
        ViewModelProvider(this).get(CharacterDetailViewModel::class.java)
    }
    private val safeArgs:CharacterDetailFragmentArgs by navArgs()

    private val controller= CharacterDetailsEpoxyController( ::onEpisodeClick)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentCharacterDetailBinding.bind(view)



        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)

        viewModel.fetchCharacter(safeArgs.characterId)

        viewModel.characterByIdLiveData.observe(viewLifecycleOwner){
            controller.items=it
            if (it==null){
                Toast.makeText(requireContext(),"not successful", Toast.LENGTH_SHORT).show()
                //be sure to return and not continue the rest of function
                return@observe
                findNavController().navigateUp()
            }
        }





    }//FUN
    private fun onEpisodeClick(episodeId:Int){
        //this is for global directions, we use it when there is a destination that we may move to them from multiple sources
        val directions=NavGraphDirections.actionGlobalToEpisodeDetailFragment(episodeId)
        findNavController().navigate(directions)
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}