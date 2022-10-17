package com.farshad.mysimplemorty.ui.characters.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.farshad.mysimplemorty.R
import com.farshad.mysimplemorty.databinding.FragmentSearchCharacterBinding
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class CharacterSearchFragment: com.farshad.mysimplemorty.BaseFragment(R.layout.fragment_search_character) {
    private var _binding: FragmentSearchCharacterBinding? = null
    private val binding get() = _binding!!
    private val viewModel:CharacterSearchViewModel by viewModels()
    private val epoxyController=CharacterSearchEpoxyController(::onCharacterSelected)

    private var currentText= ""
    private val handler=Handler(Looper.getMainLooper())
    private val searchRunnable=Runnable{
        viewModel.submitQuery(currentText)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentSearchCharacterBinding.bind(view)

        binding.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)

        //just after text change this wil work
        binding.edSearch.doAfterTextChanged {
            //set current text to what user typed
            currentText=it?.toString() ?:""
            //remove last thing that are in the que
            handler.removeCallbacks(searchRunnable)
            //run search function after 0.5 second pause which is almost after user stop typing the text
            handler.postDelayed(searchRunnable,500L)
        }

        lifecycleScope.launch{
            viewModel.flow.collectLatest {
                //at first we set local exception to null, in order to run the epoxy controller(look at epoxy controller code)
                epoxyController.localException = null
                epoxyController.submitData(it)
            }
        }
        //handing error  state  when get into fragment before user type anything
        viewModel.localExceptionEventLiveData.observe(viewLifecycleOwner){
            //if getContent is not null then set the localException State from live data and set it to epoxy display
            it.getContent()?.let { localException->
                //handle displaying local exception
                epoxyController.localException=localException
            }
        }








    }//FUN

    private fun onCharacterSelected(characterID:Int){
        val directions=CharacterSearchFragmentDirections.actionCharacterSearchFragmentToCharacterDetailFragment(characterID)
            findNavController().navigate(directions)
    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}