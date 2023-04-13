package com.swancodes.icart.ui.home

import androidx.lifecycle.*
import com.swancodes.icart.data.Product
import com.swancodes.icart.data.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HomeViewModel(private val dao: ProductDao) : ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    private val _category = MutableLiveData<List<Product>>()
    val category: LiveData<List<Product>> get() = _category

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

    init {
        getCategories()
        getAllProducts()
    }

    private fun getCategories() {
        viewModelScope.launch {
            dao.getAllCategories().flowOn(Dispatchers.IO).collectLatest {
                _categories.value = it
            }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch {
            dao.getAllProducts().flowOn(Dispatchers.IO).collectLatest {
                _products.value = it
                _category.value = it
            }
        }
    }
    fun getProductsByCategory(category: String) {
        viewModelScope.launch {
            dao.getProductsByCategory(category).flowOn(Dispatchers.IO).collectLatest {
                _category.value = it
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val dao: ProductDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(dao) as T
        } else {
            throw IllegalArgumentException("ViewModel class: ${modelClass.canonicalName} is not assignable")
        }
    }
}
