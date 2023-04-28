package com.swancodes.icart.utilities

import android.content.Context
import androidx.fragment.app.Fragment
import com.swancodes.icart.data.ProductDatabase
import com.swancodes.icart.ui.MainViewModelFactory
import com.swancodes.icart.ui.cart.CartViewModelFactory
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
            productDao = ProductDatabase.getInstance(fragment.requireContext()).productDao(),
            cartDao = ProductDatabase.getInstance(fragment.requireContext()).cartDao()
        )
    }

    fun provideProductDetailsViewModelFactory(fragment: Fragment, productId: String): ProductDetailsViewModelFactory {
        return ProductDetailsViewModelFactory(
            productDao = ProductDatabase.getInstance(fragment.requireContext()).productDao(),
            cartDao = ProductDatabase.getInstance(fragment.requireContext()).cartDao(),
            productId = productId
        )
    }

    fun provideCartViewModelFactory(fragment: Fragment): CartViewModelFactory {
        return CartViewModelFactory(
            cartDao = ProductDatabase.getInstance(fragment.requireContext()).cartDao()
        )
    }
}
