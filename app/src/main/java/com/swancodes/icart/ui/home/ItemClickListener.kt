package com.swancodes.icart.ui.home

import com.swancodes.icart.data.cart.Cart

interface ItemClickListener {
    fun onItemClick(productId: String)
    fun onAddToCart(cart: Cart)
}