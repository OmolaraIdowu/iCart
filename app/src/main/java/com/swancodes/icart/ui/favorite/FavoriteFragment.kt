package com.swancodes.icart.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.swancodes.icart.R
import com.swancodes.icart.databinding.FragmentFavoriteBinding
import com.swancodes.icart.utilities.viewBinding

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private val binding: FragmentFavoriteBinding by viewBinding(FragmentFavoriteBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}
