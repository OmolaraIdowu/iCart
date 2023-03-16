package com.swancodes.icart.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.swancodes.icart.data.Product
import com.swancodes.icart.data.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainViewModel(private val dao: ProductDao) : ViewModel() {

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            dao.getAllProducts().flowOn(Dispatchers.IO).collectLatest {
                _products.value = it
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(private val dao: ProductDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(dao) as T
        } else {
            throw IllegalArgumentException("ViewModel class: ${modelClass.canonicalName} ia not assignable")
        }
    }
}
