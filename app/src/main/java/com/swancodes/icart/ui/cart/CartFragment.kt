package com.swancodes.icart.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.swancodes.icart.R
import com.swancodes.icart.data.cart.Cart
import com.swancodes.icart.data.product.Product
import com.swancodes.icart.databinding.FragmentCartBinding
import com.swancodes.icart.utilities.InjectorUtils
import com.swancodes.icart.utilities.viewBinding

class CartFragment : Fragment(R.layout.fragment_cart), CartItemClickListener {

    private val binding: FragmentCartBinding by viewBinding(FragmentCartBinding::bind)
    private val viewModel: CartViewModel by viewModels {
        InjectorUtils.provideCartViewModelFactory(
            this
        )
    }
    private val adapter: CartAdapter by lazy { CartAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cartRecyclerView.adapter = adapter

        viewModel.cartItems.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.emptyView.isVisible = it.isEmpty()
        }

        binding.shopButton.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.toHomeFragment())
        }
    }

    override fun onCartItemClick(productId: String) {
        findNavController().navigate(CartFragmentDirections.toProductDetailsFragment(productId))
    }

    override fun onCartItemCancel(cart: Cart) {
       showDeleteDialog(cart)
    }

    override fun onQuantityUpdate(quantity: Int) {
        viewModel.setQuantity(quantity)
    }


    private fun showDeleteDialog(cart: Cart) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.delete_dialog, null)
        val yesButton = dialogView.findViewById<Button>(R.id.yesButton)
        val noButton = dialogView.findViewById<Button>(R.id.noButton)

        val alertDialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .show()

        yesButton.setOnClickListener {
            viewModel.deleteCart(cart)
            Snackbar.make(binding.root, "Item was removed from cart", Snackbar.LENGTH_SHORT).show()
            alertDialog.dismiss()
        }

        noButton.setOnClickListener {
            alertDialog.dismiss()
        }
    }
}
