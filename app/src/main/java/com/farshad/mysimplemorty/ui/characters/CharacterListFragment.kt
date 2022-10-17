package com.farshad.mysimplemorty.ui.characters

import android.os.Bundle
import android.view.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.filter
import com.farshad.mysimplemorty.BaseFragment
import com.farshad.mysimplemorty.R
import com.farshad.mysimplemorty.databinding.FragmentCharacterListBinding
import com.farshad.mysimplemorty.ui.filter.FilterModel
import com.farshad.mysimplemorty.ui.filter.FilterViewModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.lang.RuntimeException

@AndroidEntryPoint
class CharacterListFragment: BaseFragment(R.layout.fragment_character_list) {
    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!


    private val viewModel: CharactersViewModel by viewModels()
    private val filterViewModel : FilterViewModel by activityViewModels()

    private val epoxyController= CharacterListEpoxyController(::onCharacterClick)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enable menu
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentCharacterListBinding.bind(view)

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)


        //flow collect needs to run inside coroutine
        lifecycleScope.launchWhenStarted{
            viewModel.flow.collectLatest {data->

                combine(
                    filterViewModel.filterByStatusModelLiveData ,
                    filterViewModel.filterByGenderModelLiveData
                ){setOfStatusFilter , setOfGenderFilter ->

                    data.filter { toBeFilter ->
                        setOfStatusFilter.selectedStatus.all { toBeFilter.status.contains(it) }
                                &&
                        setOfGenderFilter.selectedSpecies.all { toBeFilter.gender.contains(it) }
                    }

                }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){dataForEpoxy->
                    lifecycleScope.launch { epoxyController.submitData(dataForEpoxy) }
                }



            }
        }

















    }//FUN
    private fun onCharacterClick(characterId:Int) {
        FirebaseCrashlytics.getInstance().recordException(
            RuntimeException("character Id selected=$characterId")
        )

        val directions=CharacterListFragmentDirections.actionCharacterListFragmentToCharacterDetailFragment(characterId)
        findNavController().navigate(directions)
    }

    //set menu layout
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_filter,menu)
    }
    //set on click listener for each menu item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.menuFilter ->{
                findNavController().navigate(CharacterListFragmentDirections.actionCharacterListFragmentToFilterFragment())
                true//this is just for  onOptionsItemSelected requirement
            }
            else -> super.onOptionsItemSelected(item)

        }

    }















    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}