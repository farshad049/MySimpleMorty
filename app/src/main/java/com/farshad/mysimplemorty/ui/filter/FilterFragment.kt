package com.farshad.mysimplemorty.ui.filter

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.asLiveData
import com.farshad.mysimplemorty.BaseFragment
import com.farshad.mysimplemorty.R
import com.farshad.mysimplemorty.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class FilterFragment : BaseFragment(R.layout.fragment_filter) {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!
    private val viewModel : FilterViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFilterBinding.bind(view)

        val controller = FilterEpoxyController(viewModel)

        combine(
            viewModel.filterByStatusModelLiveData ,
            viewModel.filterByGenderModelLiveData
        ){setOfStatus ,setOfSpecies ->

            val statusList = setOfStatus.status.map { status->
                UiFilter(
                    filterDisplayName = status ,
                    isSelected = setOfStatus.selectedStatus.contains(status)
                )
            }

            val speciesList = setOfSpecies.species.map { species->
                UiFilter(
                    filterDisplayName = species ,
                    isSelected = setOfSpecies.selectedSpecies.contains(species)
                )
            }

            return@combine CombinedUiFilterModel(statusList,speciesList)

        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){data->
            controller.setData(data)
        }

        binding.epoxyRecyclerView.setController(controller)











    }//FUN





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}