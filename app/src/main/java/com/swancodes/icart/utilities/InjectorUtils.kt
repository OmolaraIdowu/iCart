package com.swancodes.icart.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.swancodes.icart.data.ProductDatabase
import com.swancodes.icart.ui.MainViewModelFactory
import com.swancodes.icart.ui.home.HomeViewModelFactory
import com.swancodes.icart.ui.product.ProductDetailsViewModelFactory

object InjectorUtils {

    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        return MainViewModelFactory(
            dao = ProductDatabase.getInstance(context.applicationContext).productDao()
        )
    }

    fun provideHomeViewModelFactory(fragment: Fragment): HomeViewModelFactory {
        return HomeViewModelFactory(
            dao = ProductDatabase.getInstance(fragment.requireContext()).productDao()
        )
    }

    fun provideProductDetailsViewModelFactory(fragment: Fragment, productId: String): ProductDetailsViewModelFactory {
        return ProductDetailsViewModelFactory(
            dao = ProductDatabase.getInstance(fragment.requireContext()).productDao(),
            productId = productId
        )
    }
}
