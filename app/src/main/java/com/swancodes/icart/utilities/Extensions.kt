package com.swancodes.icart.utilities

import android.widget.ImageView
import coil.load
import com.swancodes.icart.R

fun ImageView.loadImage(imageUrl: String) {
    load(imageUrl) {
        placeholder(R.drawable.loading_animation)
        error(R.drawable.ic_broken_image)
    }
}