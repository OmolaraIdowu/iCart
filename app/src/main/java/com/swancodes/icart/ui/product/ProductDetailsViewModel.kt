package com.swancodes.icart.ui.product

import android.util.Log
import androidx.lifecycle.*
import com.swancodes.icart.data.Product
import com.swancodes.icart.data.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsViewModel(private val dao: ProductDao, productId: String): ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> get() = _quantity

    init {
        getProductById(productId)
    }

    private fun getProductById(productId: String) {
        viewModelScope.launch {
            dao.getProductById(productId).flowOn(Dispatchers.IO).collectLatest {
                _product.value = it
                _quantity.value = 0
            }
        }
    }

    fun setQuantity(quantity: Int) {
        _quantity.value = quantity
    }

    fun updateProduct() {
        val currentProduct = _product.value
        currentProduct?.let {
            val currentQuantity = _quantity.value!!
            it.quantityRemaining -= currentQuantity
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    dao.updateProduct(it)
                }
                Log.d("ProductDetailsViewModel", "Product updated successfully: $it")
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