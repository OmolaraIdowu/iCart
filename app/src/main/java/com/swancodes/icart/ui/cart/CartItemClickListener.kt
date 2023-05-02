package com.swancodes.icart.ui.cart

import com.swancodes.icart.data.cart.Cart

interface CartItemClickListener {
    fun onCartItemClick(productId: String)
    fun onCartItemCancel(cart: Cart)
    fun onQuantityUpdate(quantity: Int)
}