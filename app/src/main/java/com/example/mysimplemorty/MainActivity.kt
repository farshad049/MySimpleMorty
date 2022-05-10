package com.example.mysimplemorty

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit:Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val rickAndMortyService : RickAndMortyService=retrofit.create(RickAndMortyService::class.java)

        //handling on response and on failure for retrofit service
        rickAndMortyService.getCharacterById(10).enqueue(object :Callback<GetCharacterByIDResponse>{
            override fun onResponse(call: Call<GetCharacterByIDResponse>, response: Response<GetCharacterByIDResponse>) {
                Log.i("MainActivity",response.toString())

                if (!response.isSuccessful){
                    Toast.makeText(this@MainActivity,"not successful",Toast.LENGTH_SHORT).show()
                    return
                }
                val body=response.body()!!
                Picasso.get().load(body.image).into(binding.ivHeader)
                if (body.gender.equals("male",false)){
                    binding.ivGender.setImageResource(R.drawable.ic_male_24)
                }else{
                    binding.ivGender.setImageResource(R.drawable.ic_female_24)
                }
                binding.tvName.text=body.name
                binding.tvStatus.text=body.status
                binding.tvOrigin.text= body.origin.name
                binding.tvSpecies.text=body.species
            }
            override fun onFailure(call: Call<GetCharacterByIDResponse>, t: Throwable) {
                Log.i("MainActivity", t.message ?:"null message")
            }
        })



    }//FUN




}