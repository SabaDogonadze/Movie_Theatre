package com.example.movietheatre

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.movietheatre.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment?.findNavController()
            ?: throw IllegalStateException("NavController not found. Check your layout's NavHostFragment ID.")

        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            if (menuItem.itemId == navController.currentDestination?.id) {
                return@setOnItemSelectedListener false
            }

            val navOptions = NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setPopUpTo(menuItem.itemId, inclusive = true)
                .build()

            navController.navigate(menuItem.itemId, null, navOptions)
            true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val bottomNavDestinations = setOf(
                R.id.id_home_fragment,
            )

            binding.bottomNavigationView.visibility = if (destination.id in bottomNavDestinations) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.bottomNavigationView.menu.findItem(destination.id)?.isChecked = true
        }
    }
}