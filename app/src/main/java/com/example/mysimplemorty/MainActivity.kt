package com.example.mysimplemorty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mysimplemorty.characters.CharacterListPagingEpoxyController
import com.example.mysimplemorty.characters.CharactersViewModel
import com.example.mysimplemorty.databinding.ActivityCharacterListBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityCharacterListBinding? = null
    private val binding get() = _binding!!
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityCharacterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val viewModel:SharedViewModel by viewModels()



        //enable the nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        //enable the action bar
        appBarConfiguration= AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appBarConfiguration)















    }//FUN
    //enable back button on action bar
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }





    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}