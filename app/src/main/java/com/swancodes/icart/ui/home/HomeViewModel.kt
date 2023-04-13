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

    private val _products: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> get() = _products

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
            dao.getAllCategories().flowOn(Dispatchers.IO).collectLatest {
                _categories.value = it
            }
        }
    }

    fun getAllProducts(category: String) {
        viewModelScope.launch {
            dao.getProductsByCategory(category).flowOn(Dispatchers.IO).collectLatest {
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
