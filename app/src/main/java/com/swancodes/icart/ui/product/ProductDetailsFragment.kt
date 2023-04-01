package com.swancodes.icart.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.swancodes.icart.R
import com.swancodes.icart.data.Product
import com.swancodes.icart.databinding.FragmentProductDetailsBinding
import com.swancodes.icart.utilities.loadImage
import com.swancodes.icart.utilities.viewBinding
import java.text.NumberFormat
import java.util.*

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {
    private val binding: FragmentProductDetailsBinding by viewBinding(FragmentProductDetailsBinding::bind)
    private val args by navArgs<ProductDetailsFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        displayProductInfo(args.product)
    }

    private fun displayProductInfo(it: Product) {
        binding.productImage.loadImage(it.imageUrl)
        binding.productName.text = it.name
        binding.productHeader.text = it.name
        binding.productHeader.isSelected = true
        val formatNum = NumberFormat.getNumberInstance(Locale.getDefault()).format(it.price)
        binding.productPrice.text = getString(R.string.currency, formatNum)
        binding.ratingBar.rating = it.rating.toFloat()
        binding.ratingTextView.text = it.rating.toString()
        binding.productDescription.text = it.description
        binding.quantityRemaining.text =
            getString(R.string.quantity_remaining, it.quantityRemaining)
    }
}