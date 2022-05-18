package com.example.mysimplemorty

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mysimplemorty.databinding.ActivityCharacterDetailBinding



class CharacterDetailActivity : AppCompatActivity() {
    private var _binding: ActivityCharacterDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel:SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }
    private val controller=CharacterDetailsEpoxyController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCharacterDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)













    }//FUN

    //enable back button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}