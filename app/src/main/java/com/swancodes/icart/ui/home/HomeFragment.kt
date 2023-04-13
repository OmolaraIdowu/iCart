package com.swancodes.icart.ui.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.swancodes.icart.R
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
    private val adapter: HomeAdapter by lazy { HomeAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeRecyclerView.adapter = adapter

        setupObservers()
        setupToolbar()

        binding.categoryChipGroup.setOnCheckedStateChangeListener { _, checkedIds ->
            viewModel.setCheckedCategoryId(checkedIds.first())
        }
    }

    private fun setupToolbar() = with(binding) {
        toolbar.apply {
            setNavigationOnClickListener {
                findNavController().navigate(
                    HomeFragmentDirections.toSearchFragment()
                )
            }
            setOnMenuItemClickListener {
                if (it.itemId == R.id.action_filter_toggle) {
                    viewModel.setCategoryListState(!binding.categoryChipGroup.isVisible)
                }
                true
            }
        }
    }

    private fun setupObservers() {
        viewModel.categories.observe(viewLifecycleOwner, ::setupCategorySelection)

        viewModel.products.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.categoryListState.observe(viewLifecycleOwner) {
            binding.categoryChipGroup.isVisible = it
        }
    }

    private fun setupCategorySelection(categories: List<String>) {
        categories.forEachIndexed { index, category ->
            val chip = createChip(text = category, id = index + 1)

            chip.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.getAllProducts(category)
                }
            }

            binding.categoryChipGroup.addView(chip, index)
        }
        val chip = createChip(text = getString(R.string.all), id = 0)
        chip.isChecked = true
        chip.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.getAllProducts("")
            }
        }
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

    override fun onItemClick(productId: String) {
        val action = HomeFragmentDirections.toProductDetailsFragment(productId)
        findNavController().navigate(action)
    }

    override fun onResume() {
        super.onResume()

        viewModel.checkedCategory.observe(viewLifecycleOwner) {
            binding.categoryChipGroup.check(it)
        }
    }
}
