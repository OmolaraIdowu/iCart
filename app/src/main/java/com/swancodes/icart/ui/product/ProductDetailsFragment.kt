package com.swancodes.icart.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.swancodes.icart.R
import com.swancodes.icart.data.Product
import com.swancodes.icart.databinding.FragmentProductDetailsBinding
import com.swancodes.icart.utilities.InjectorUtils
import com.swancodes.icart.utilities.loadImage
import com.swancodes.icart.utilities.viewBinding
import java.text.NumberFormat
import java.util.*

class ProductDetailsFragment : Fragment(R.layout.fragment_product_details) {

    private val binding: FragmentProductDetailsBinding by viewBinding(FragmentProductDetailsBinding::bind)
    private val args by navArgs<ProductDetailsFragmentArgs>()
    private val viewModel: ProductDetailsViewModel by viewModels {
        InjectorUtils.provideProductDetailsViewModelFactory(
            this, args.productId
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.product.observe(viewLifecycleOwner) {
            displayProductInfo(it)
        }
    }

    private fun displayProductInfo(product: Product) {
        binding.productImage.loadImage(product.imageUrl)
        binding.productName.text = product.name
        binding.productHeader.text = product.name
        binding.productHeader.isSelected = true
        val formatNum = NumberFormat.getNumberInstance(Locale.getDefault()).format(product.price)
        binding.productPrice.text = getString(R.string.currency, formatNum)
        binding.ratingBar.rating = product.rating.toFloat()
        binding.ratingTextView.text = product.rating.toString()
        binding.productDescription.text = product.description
        binding.quantityRemaining.text =
            getString(R.string.quantity_remaining, product.quantityRemaining)
    }
}