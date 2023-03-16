package com.swancodes.icart.ui.home

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.swancodes.icart.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        *//*binding.allItemsButton.setOnClickListener {
            binding.allItemsTextView.setTypeface(null, if (it.isSelected) Typeface.NORMAL else Typeface.BOLD)
            it.isSelected = !it.isSelected
        }

        binding.chairButton.setOnClickListener {
            binding.chairTextView.setTypeface(null, if (it.isSelected) Typeface.NORMAL else Typeface.BOLD)
            it.isSelected = !it.isSelected
        }

        binding.tableButton.setOnClickListener {
            binding.tableTextView.setTypeface(null, if (it.isSelected) Typeface.NORMAL else Typeface.BOLD)
            it.isSelected = !it.isSelected
        }

        binding.bedButton.setOnClickListener {
            binding.bedTextView.setTypeface(null, if (it.isSelected) Typeface.NORMAL else Typeface.BOLD)
            it.isSelected = !it.isSelected
        }*//*
    }*/
}