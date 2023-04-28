package com.swancodes.icart.ui.cart

import androidx.lifecycle.*
import com.swancodes.icart.data.cart.Cart
import com.swancodes.icart.data.cart.CartDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartViewModel(private val cartDao: CartDao) : ViewModel() {

    private val _cartItems: MutableLiveData<List<Cart>> = MutableLiveData()
    val cartItems: LiveData<List<Cart>> get() = _cartItems

    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> get() = _quantity

    init {
        getAllCartItems()
    }

    private fun getAllCartItems() {
        viewModelScope.launch {
            cartDao.getAllCartItems().flowOn(Dispatchers.IO).collectLatest {
                _cartItems.value = it
                _quantity.value = 0
            }
        }
    }

    fun setQuantity(quantity: Int) {
        _quantity.value = quantity
    }

    fun deleteCart(cart: Cart) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                cartDao.deleteCartItem(cart)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CartViewModelFactory(private val cartDao: CartDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            CartViewModel(cartDao) as T
        } else {
            throw IllegalArgumentException("ViewModel class: ${modelClass.canonicalName} is not assignable")
        }
    }
}
