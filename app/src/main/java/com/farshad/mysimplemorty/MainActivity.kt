package com.farshad.mysimplemorty

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.farshad.mysimplemorty.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //enable the nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        //enable the action bar
        appBarConfiguration= AppBarConfiguration(
            //set this to fragment to be as top level fragment , so back button and nav bar wont be shown on them
            topLevelDestinationIds = setOf(R.id.characterListFragment,R.id.episodeListFragment),
            //this is for setting the drawer layout
            drawerLayout = binding.drawerLayout
            )

        //set up fragment title in toolbar
        setupActionBarWithNavController(navController,appBarConfiguration)

        //enable navigation drawer
        findViewById<NavigationView>(R.id.nav_view).setupWithNavController(navController)
        //set the start page to be selected by default in drawer menu
       // findViewById<NavigationView>(R.id.nav_view).setCheckedItem(navController.graph.startDestinationId)










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