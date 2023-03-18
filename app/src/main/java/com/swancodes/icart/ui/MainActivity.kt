package com.swancodes.icart.ui

import android.os.Bundle
import android.widget.Toast
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

        mainViewModel.products.observe(this) {
            // TODO: Remove when actual implementation is available
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
    }
}
