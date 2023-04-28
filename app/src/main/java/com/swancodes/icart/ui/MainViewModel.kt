package com.swancodes.icart.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.swancodes.icart.data.product.ProductDao

class MainViewModel(private val dao: ProductDao) : ViewModel() {

}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val dao: ProductDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(dao) as T
        } else {
            throw IllegalArgumentException("ViewModel class: ${modelClass.canonicalName} is not assignable")
        }
    }
}
