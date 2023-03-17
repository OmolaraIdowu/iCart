package com.swancodes.icart.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.swancodes.icart.R
import com.swancodes.icart.databinding.ActivityMainBinding
import com.swancodes.icart.ui.favorite.FavoriteFragment
import com.swancodes.icart.ui.home.HomeFragment
import com.swancodes.icart.ui.profile.ProfileFragment
import com.swancodes.icart.utilities.InjectorUtils

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

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> showFragment(HomeFragment())
                R.id.favorite -> showFragment(FavoriteFragment())
                R.id.profile -> showFragment(ProfileFragment())
            }
            true
        }
    }
    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, fragment)
            commit()
        }
    }
}
