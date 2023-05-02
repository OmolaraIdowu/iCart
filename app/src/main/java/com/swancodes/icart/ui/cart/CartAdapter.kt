package com.swancodes.icart.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.swancodes.icart.R
import com.swancodes.icart.data.cart.Cart
import com.swancodes.icart.databinding.CartItemBinding
import com.swancodes.icart.utilities.loadImage
import java.text.NumberFormat
import java.util.*

class CartAdapter(private val listener: CartItemClickListener) :
    ListAdapter<Cart, CartAdapter.CartViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Cart>() {
            override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
                return oldItem.product.productId == newItem.product.productId
            }

            override fun areContentsTheSame(
                oldItem: Cart,
                newItem: Cart
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class CartViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: Cart) = with(binding) {
            val product = cartItem.product
            val formatNum =
                NumberFormat.getNumberInstance(Locale.getDefault()).format(product.price)
            cartItemTitle.text = product.name
            cartItemPrice.text = root.context.getString(R.string.currency, formatNum)
            cartItemImageView.loadImage(product.imageUrl)
            if (product.quantityRemaining == 0) {
                cartQuantityRemaining.text = root.context.getString(R.string.out_of_stock)
            } else {
                cartQuantityRemaining.text =
                    root.context.getString(R.string.quantity_remaining, product.quantityRemaining)
            }

            var quantity = cartItem.quantity
            var newQuantityRemaining = product.quantityRemaining - quantity
            val cart = Cart(product, newQuantityRemaining)

            cartQuantityTextView.text = quantity.toString()

            increaseButton.setOnClickListener {
                if (quantity < product.quantityRemaining) {
                    listener.onQuantityUpdate(quantity)
                    quantity += 1
                    newQuantityRemaining = product.quantityRemaining - quantity
                    if (newQuantityRemaining == 0) {
                        cartQuantityRemaining.text = root.context.getString(R.string.out_of_stock)
                    } else {
                        cartQuantityRemaining.text = root.context.getString(R.string.quantity_remaining, newQuantityRemaining)
                    }
                } else {
                    Snackbar.make(binding.root, "Cannot add more items", Snackbar.LENGTH_SHORT).show()
                }
                cartQuantityTextView.text = quantity.toString()
            }

            decreaseButton.setOnClickListener {
                if (quantity > 1) {
                    listener.onQuantityUpdate(quantity)
                    quantity -= 1
                    newQuantityRemaining = product.quantityRemaining - quantity
                    cartQuantityRemaining.text = root.context.getString(R.string.quantity_remaining, newQuantityRemaining)
                } else {
                    Snackbar.make(binding.root, "Cannot decrease quantity further", Snackbar.LENGTH_SHORT).show()
                }
                cartQuantityTextView.text = quantity.toString()
            }

            cancelButton.setOnClickListener {
                listener.onCartItemCancel(cart)
            }

            root.setOnClickListener {
                listener.onCartItemClick(product.productId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            CartItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}