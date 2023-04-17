package com.swancodes.icart.ui.product

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
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

        viewModel.quantity.observe(viewLifecycleOwner) { quantity ->
            updateQuantity(quantity)
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
        if (product.quantityRemaining == 0) {
            binding.quantityRemaining.text = getString(R.string.out_of_stock)
        } else {
            binding.quantityRemaining.text =
                getString(R.string.quantity_remaining, product.quantityRemaining)
        }
    }

    private fun updateQuantity(quantity: Int) {
        binding.quantityTextView.text = quantity.toString()
        val product = viewModel.product.value!!
        val newQuantityRemaining = product.quantityRemaining - quantity

        if (product.quantityRemaining == 0) {
            binding.quantityRemaining.text = getString(R.string.out_of_stock)
        } else {
            binding.quantityRemaining.text = getString(R.string.quantity_remaining, newQuantityRemaining)
        }

        binding.decreaseButton.setOnClickListener {
            if (quantity > 0) {
                viewModel.setQuantity(quantity - 1)
                binding.quantityRemaining.text =
                    getString(R.string.quantity_remaining, newQuantityRemaining + 1)
            } else {
                Snackbar.make(binding.root, "Cannot decrease quantity further", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.increaseButton.setOnClickListener {
            if (quantity < product.quantityRemaining) {
                viewModel.setQuantity(quantity + 1)
                if (newQuantityRemaining - 1 == 0) {
                    binding.quantityRemaining.text = getString(R.string.out_of_stock)
                } else {
                    binding.quantityRemaining.text = getString(R.string.quantity_remaining,
                        newQuantityRemaining - 1)
                }
            } else {
                Snackbar.make(binding.root, "Cannot add more items", Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.productCheckoutButton.setOnClickListener {
            if (quantity == 0) {
                Snackbar.make(binding.root, "Please select at least one item to checkout", Snackbar.LENGTH_SHORT).show()
            } else {
                viewModel.updateProduct()
                findNavController().navigate(ProductDetailsFragmentDirections.toCartFragment())
            }
        }
    }

}