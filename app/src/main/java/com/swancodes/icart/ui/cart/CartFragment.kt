package com.swancodes.icart.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.swancodes.icart.R
import com.swancodes.icart.databinding.FragmentCartBinding
import com.swancodes.icart.utilities.viewBinding

class CartFragment : Fragment(R.layout.fragment_cart) {

    private val binding: FragmentCartBinding by viewBinding(FragmentCartBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
