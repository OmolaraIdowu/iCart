package com.swancodes.icart.ui.home

import com.swancodes.icart.data.Product

interface ItemClickListener {
    fun onItemClick(productId: String)
}