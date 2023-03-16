package com.swancodes.icart.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.swancodes.icart.R
import com.swancodes.icart.databinding.ActivityMainBinding
import com.swancodes.icart.ui.home.FavoriteFragment
import com.swancodes.icart.ui.home.HomeFragment
import com.swancodes.icart.ui.home.ProfileFragment
import com.swancodes.icart.utilities.FAVORITE
import com.swancodes.icart.utilities.HOME
import com.swancodes.icart.utilities.InjectorUtils
import com.swancodes.icart.utilities.PROFILE

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels { InjectorUtils.provideMainViewModelFactory(this) }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showFragment(HomeFragment())

        mainViewModel.products.observe(this) {
            // TODO: Remove when actual implementation is available
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
        mainViewModel.navigationItem.observe(this) { navigationItem ->
            when (navigationItem) {
                HOME -> {
                    // Handle navigation to the home destination
                    showFragment(HomeFragment())
                }
                FAVORITE -> {
                    // Handle navigation to the cart destination
                    showFragment(FavoriteFragment())
                }
                PROFILE -> {
                    // Handle navigation to the profile destination
                    showFragment(ProfileFragment())
                }
            }
        }
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> mainViewModel.setNavigationItem(HOME)
                R.id.favorite -> mainViewModel.setNavigationItem(FAVORITE)
                R.id.profile -> mainViewModel.setNavigationItem(PROFILE)
            }
            true
        }
    }
    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commitNow()
    }
}
