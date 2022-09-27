package com.skapps.eys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.skapps.eys.databinding.ActivityMainBinding
import github.com.st235.lib_expandablebottombar.navigation.ExpandableBottomBarNavigationUI

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val navHostFragment=supportFragmentManager.findFragmentById(com.skapps.eys.R.id.HostFragment)
        navController = navHostFragment!!.findNavController()
        ExpandableBottomBarNavigationUI.setupWithNavController(binding.bottomNav,navController)
        setContentView(view)
       // val bottomNavigationView = findViewById<BottomNavigationView>(com.skapps.eys.R.id.bottomNav) as ExpandableBottomBarNavigationUI
        navController.addOnDestinationChangedListener{_, destination, _ ->
            binding.bottomNav.visibility = if (destination.id==com.skapps.eys.R.id.loginFragment || destination.id==com.skapps.eys.R.id.signUpFragment) {
                View.GONE } else {
                View.VISIBLE
            }
        }
    }
}