package com.swancodes.icart.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.swancodes.icart.R
import com.swancodes.icart.data.Product
import com.swancodes.icart.databinding.HomeItemBinding
import com.swancodes.icart.utilities.loadImage
import java.text.NumberFormat
import java.util.*

class HomeAdapter(
    private val listener: ItemClickListener
) : ListAdapter<Product, HomeAdapter.HomeViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class HomeViewHolder(
        private val binding: HomeItemBinding,
        private val listener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productItem: Product) = with(binding) {
            homeItemTitle.text = productItem.name
            val formatNum = NumberFormat.getNumberInstance(Locale.US).format(productItem.price)
            homeItemPrice.text = binding.root.context.getString(R.string.currency, formatNum)
            homeItemImageView.loadImage(productItem.imageUrl)
            homeShopButton.setOnClickListener {
                // This should add item to cart
            }
            root.setOnClickListener {
                listener.onItemClick(productItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            HomeItemBinding.bind(
                LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
            ), listener
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

