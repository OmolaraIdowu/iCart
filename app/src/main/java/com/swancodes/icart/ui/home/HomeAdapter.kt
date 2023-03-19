package com.swancodes.icart.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.swancodes.icart.R
import com.swancodes.icart.data.Product
import com.swancodes.icart.databinding.HomeItemBinding

class HomeAdapter(
    private val productList: List<Product>,
    private val listener: ItemClickListener
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(
        private val binding: HomeItemBinding,
        private val listener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productItem: Product, context: Context) = with(binding) {
            homeItemTitle.text = productItem.name
            homeItemPrice.text = context.getString(R.string.currency, productItem.price)
            homeItemImageView.load(productItem.imageUrl) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
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
        holder.bind(productList[position], holder.itemView.context)
    }

    override fun getItemCount() = productList.size
}
