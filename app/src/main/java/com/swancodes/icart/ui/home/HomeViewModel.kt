package com.swancodes.icart.ui.home

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

class HomeViewModel(private val productDao: ProductDao, private val cartDao: CartDao) :
    ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

    private val _cartItems: MutableLiveData<List<Cart>> = MutableLiveData()
    val cartItems: LiveData<List<Cart>> get() = _cartItems

    private val _categoryListState = MutableLiveData(false)
    val categoryListState: LiveData<Boolean> get() = _categoryListState

    private val _checkedCategory = MutableLiveData<Int>()
    val checkedCategory: LiveData<Int> = _checkedCategory

    init {
        getCategories()
        getAllProducts("")
    }

    private fun getCategories() {
        viewModelScope.launch {
            productDao.getAllCategories().flowOn(Dispatchers.IO).collectLatest {
                _categories.value = it
            }
        }
    }

    fun getAllProducts(category: String) {
        viewModelScope.launch {
            productDao.getProductsByCategory(category).flowOn(Dispatchers.IO).collectLatest {
                _products.value = it
            }
        }
    }

    fun setCategoryListState(state: Boolean) {
        _categoryListState.value = state
    }

    fun setCheckedCategoryId(id: Int) {
        _checkedCategory.value = id
    }

    fun insertCartItem(cart: Cart) {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                cartDao.insertCartItem(cart)
            }
            Log.d("HomeViewModel", "Product added to cart successfully: $cart")
        }
    }

}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val productDao: ProductDao, private val cartDao: CartDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(productDao, cartDao) as T
        } else {
            throw IllegalArgumentException("ViewModel class: ${modelClass.canonicalName} is not assignable")
        }
    }
}
