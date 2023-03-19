package com.swancodes.icart.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.swancodes.icart.R
import com.swancodes.icart.databinding.FragmentProductDetailsBinding
import com.swancodes.icart.utilities.viewBinding

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    private val binding: FragmentProductDetailsBinding by viewBinding(FragmentProductDetailsBinding::bind)
    private val args by navArgs<ProductDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backArrow.setOnClickListener {
            findNavController().navigateUp()
        }
        args.product.let {
            binding.productImage.load(it.imageUrl) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_broken_image)
            }
            binding.productName.text = it.name
            binding.productPrice.text = getString(R.string.currency, it.price)
            binding.ratingTextView.text = it.rating.toString()
            binding.productDescription.text = it.description
            binding.quantityRemaining.text = getString(R.string.quantity_remaining, it.quantityRemaining)
        }
    }
}