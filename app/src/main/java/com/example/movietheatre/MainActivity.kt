package com.example.movietheatre

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.movietheatre.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()

        if (intent.action == Intent.ACTION_VIEW) {
            handleDeepLink(intent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (intent.action == Intent.ACTION_VIEW) {
            handleDeepLink(intent)
        }
    }

    private fun handleDeepLink(intent: Intent) {
        val navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
            .navController
        navController.handleDeepLink(intent)
    }

    private fun setupNavigation() {
        val navController = (supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
            .navController

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            if (navController.currentDestination?.id == menuItem.itemId) return@setOnItemSelectedListener false

            val options = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(menuItem.itemId, inclusive = true)
                .build()

            navController.navigate(menuItem.itemId, null, options)
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.hierarchy.any { it.id == R.id.home_graph }) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.bottomNavigationView.menu.findItem(R.id.home_graph)?.isChecked = true

            } else if (destination.hierarchy.any { it.id == R.id.shop_graph }) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.bottomNavigationView.menu.findItem(R.id.shop_graph)?.isChecked = true

            } else if (destination.id == com.example.feature.profile.presentation.R.id.id_profile_fragment) {
                binding.bottomNavigationView.visibility = View.VISIBLE
                binding.bottomNavigationView.menu.findItem(R.id.profile_graph)?.isChecked = true

            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }

}