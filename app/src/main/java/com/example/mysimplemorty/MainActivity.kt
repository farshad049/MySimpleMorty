package com.example.mysimplemorty

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import com.example.mysimplemorty.databinding.ActivityMainBinding
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.picasso.Picasso
import retrofit2.*
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel:SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }
    private val controller=CharacterDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.epoxyRecyclerView.setControllerAndBuildModels(controller)


        viewModel.refreshCharacter(50)
        viewModel.characterByIdLiveData.observe(this){
            controller.items=it

            if (it==null){
                Toast.makeText(this@MainActivity,"not successful",Toast.LENGTH_SHORT).show()
                //be sure to return and not continue the rest of function
                return@observe
            }

        }








    }//FUN


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}