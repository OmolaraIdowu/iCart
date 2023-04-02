package com.swancodes.icart.ui.product

import androidx.lifecycle.*
import com.swancodes.icart.data.Product
import com.swancodes.icart.data.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class ProductDetailsViewModel(private val dao: ProductDao, productId: String): ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    init {
        getProductById(productId)
    }

    private fun getProductById(productId: String) {
        viewModelScope.launch {
           dao.getProductById(productId).flowOn(Dispatchers.IO).collectLatest {
               _product.value = it
           }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ProductDetailsViewModelFactory(private val dao: ProductDao, private val productId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            ProductDetailsViewModel(dao, productId) as T
        } else {
            throw IllegalArgumentException("ViewModel class: ${modelClass.canonicalName} is not assignable")
        }
    }
}