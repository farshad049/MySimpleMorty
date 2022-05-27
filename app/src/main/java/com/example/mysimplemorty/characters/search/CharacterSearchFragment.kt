package com.example.mysimplemorty.characters.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mysimplemorty.R
import com.example.mysimplemorty.databinding.FragmentSearchCharacterBinding
import kotlinx.coroutines.Runnable


class CharacterSearchFragment:Fragment(R.layout.fragment_search_character) {
    private var _binding: FragmentSearchCharacterBinding? = null
    private val binding get() = _binding!!
    private val viewModel:CharacterSearchViewModel by viewModels()

    private var currentText=""
    private val handler=Handler(Looper.getMainLooper())
    private val searchRunnable=Runnable{
        println(currentText)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding=FragmentSearchCharacterBinding.bind(view)


        //just after text change this wil work
        binding.edSearch.doAfterTextChanged {
            currentText=it?.toString() ?:""
            //remove last thing that are in the que
            handler.removeCallbacks(searchRunnable)
            //run search function after 0.5 second pause which is almost after user stop typing the text
            handler.postDelayed(searchRunnable,500)

        }






    }//FUN





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}