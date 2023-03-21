package com.swancodes.icart.ui.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.swancodes.icart.R
import com.swancodes.icart.data.Product
import com.swancodes.icart.databinding.FragmentHomeBinding
import com.swancodes.icart.utilities.InjectorUtils
import com.swancodes.icart.utilities.viewBinding
import java.util.*

class HomeFragment : Fragment(R.layout.fragment_home), ItemClickListener {
    private val binding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels {
        InjectorUtils.provideHomeViewModelFactory(
            this
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = HomeAdapter(this)
        binding.homeRecyclerView.adapter = adapter

        viewModel.categories.observe(viewLifecycleOwner, ::setupCategorySelection)
        binding.categoryChipGroup.setOnCheckedStateChangeListener { group, checkedId ->
            Toast.makeText(requireContext(), "CheckedId: $checkedId", Toast.LENGTH_SHORT).show()
        }

        viewModel.products.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupCategorySelection(categories: List<String>) {
        categories.forEachIndexed { index, category ->
            val chip = createChip(text = category, id = index + 1)
            binding.categoryChipGroup.addView(chip, index)
        }
        val chip = createChip(text = getString(R.string.all), id = 0)
        chip.isChecked = true
        binding.categoryChipGroup.addView(chip, 0)
    }

    private fun createChip(text: String, id: Int): Chip {
        val chip = Chip(requireContext(), null, R.attr.customChipStyle)
        chip.text = text.uppercase(Locale.getDefault())
        chip.id = id
        chip.typeface = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.poppins),
            Typeface.NORMAL
        )
        return chip
    }

    override fun onItemClick(product: Product) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(product)
        findNavController().navigate(action)
    }
}

