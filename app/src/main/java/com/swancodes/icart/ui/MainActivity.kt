package com.swancodes.icart.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.swancodes.icart.R
import com.swancodes.icart.databinding.ActivityMainBinding
import com.swancodes.icart.utilities.InjectorUtils
import com.swancodes.icart.utilities.viewBinding

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        InjectorUtils.provideMainViewModelFactory(
            this
        )
    }
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment ||
                destination.id == R.id.favoriteFragment ||
                destination.id == R.id.cartFragment) {

                binding.bottomNavView.visibility = View.VISIBLE
            } else {
                binding.bottomNavView.visibility = View.GONE
            }
        }

        mainViewModel.products.observe(this) {

        }
    }
}
