package com.swancodes.icart.utilities

import android.content.Context
import com.swancodes.icart.data.ProductDatabase
import com.swancodes.icart.ui.MainViewModelFactory

object InjectorUtils {

    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        return MainViewModelFactory(
            dao = ProductDatabase.getInstance(context.applicationContext).productDao()
        )
    }
}
