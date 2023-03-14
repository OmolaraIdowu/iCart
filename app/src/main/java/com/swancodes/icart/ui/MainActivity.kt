package com.swancodes.icart.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.swancodes.icart.R
import com.swancodes.icart.utilities.InjectorUtils

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels { InjectorUtils.provideMainViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.products.observe(this) {
            // TODO: Remove when actual implementation is available
            Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }
    }
}
