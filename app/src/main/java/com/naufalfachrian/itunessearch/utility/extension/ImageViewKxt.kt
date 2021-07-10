package com.naufalfachrian.itunessearch.utility.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.setImageFromUrlString(urlString: String) {
    Glide.with(this)
        .load(urlString)
        .into(this)
}