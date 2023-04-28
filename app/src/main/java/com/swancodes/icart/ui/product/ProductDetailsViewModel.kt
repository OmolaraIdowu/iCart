package com.swancodes.icart.ui.product

import android.util.Log
import androidx.lifecycle.*
import com.swancodes.icart.data.cart.Cart
import com.swancodes.icart.data.cart.CartDao
import com.swancodes.icart.data.product.Product
import com.swancodes.icart.data.product.ProductDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductDetailsViewModel(
    private val productDao: ProductDao,
    private val cartDao: CartDao,
    productId: String
) : ViewModel() {

    private val _product = MutableLiveData<Product>()
    val product: LiveData<Product> get() = _product

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> get() = _quantity

    init {
        getProductById(productId)
    }

    private fun getProductById(productId: String) {
        viewModelScope.launch {
            productDao.getProductById(productId).flowOn(Dispatchers.IO).collectLatest {
                _product.value = it
                _quantity.value = 0
            }
        }
    }

    fun setQuantity(quantity: Int) {
        _quantity.value = quantity
    }

    fun insertCartItem(cart: Cart) {
        val currentProduct = _product.value
        currentProduct?.let {
            val currentQuantity = _quantity.value!!
            it.quantityRemaining -= currentQuantity
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    cartDao.insertCartItem(cart)
                    Log.d("ProductDetailsViewModel", "Product added to cart successfully: $cart")
                }
            }
        }
    }

        /* fun updateProduct() {
             val currentProduct = _product.value
             currentProduct?.let {
                 val currentQuantity = _quantity.value!!
                 it.quantityRemaining -= currentQuantity
                 viewModelScope.launch {
                     withContext(Dispatchers.IO) {
                         productDao.updateProduct(it)
                     }
                     Log.d("ProductDetailsViewModel", "Product updated successfully: $it")
                 }
             }
         }*/
}

    @Suppress("UNCHECKED_CAST")
    class ProductDetailsViewModelFactory(
        private val productDao: ProductDao,
        private val cartDao: CartDao,
        private val productId: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
                ProductDetailsViewModel(productDao, cartDao, productId) as T
            } else {
                throw IllegalArgumentException("ViewModel class: ${modelClass.canonicalName} is not assignable")
            }
        }
    }