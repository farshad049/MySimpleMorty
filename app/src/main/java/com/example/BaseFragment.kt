package com.example

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavDirections
import com.example.mysimplemorty.MainActivity
import com.example.mysimplemorty.SharedViewModel

abstract class BaseFragment:Fragment() {
    protected val mainActivity: MainActivity
        get() = activity as MainActivity

    val sharedViewModel:SharedViewModel by activityViewModels()

    protected fun navigateViaNavGraphSafeArg(navDirections: NavDirections) {
        mainActivity.navController.navigate(navDirections)
    }
}