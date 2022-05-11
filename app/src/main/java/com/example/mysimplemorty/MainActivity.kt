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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel:SharedViewModel by lazy {
            ViewModelProvider(this).get(SharedViewModel::class.java)
        }


        viewModel.refreshCharacter(50)
        viewModel.characterByIdLiveData.observe(this){
            if (it==null){
                Toast.makeText(this@MainActivity,"not successful",Toast.LENGTH_SHORT).show()
                //be sure to return and not continue the rest of function
                return@observe
            }
            Picasso.get().load(it.image).into(binding.ivHeader)
                if (it.gender.equals("male",false)){
                    binding.ivGender.setImageResource(R.drawable.ic_male_24)
                }else{
                    binding.ivGender.setImageResource(R.drawable.ic_female_24)
                }
                binding.tvName.text=it.name
                binding.tvStatus.text=it.status
                binding.tvOrigin.text= it.origin.name
                binding.tvSpecies.text=it.species
        }








    }//FUN




}