package com.swancodes.icart.ui.home

import android.widget.ImageView
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

class HomeViewModel(private val dao: ProductDao) : ViewModel() {

    private val _categories = MutableLiveData<List<String>>()
    val categories: LiveData<List<String>> get() = _categories

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            dao.getAllCategories().flowOn(Dispatchers.IO).collectLatest {
                _categories.value = it
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
            throw IllegalArgumentException("ViewModel class: ${modelClass.canonicalName} ia not assignable")
        }
    }
}
