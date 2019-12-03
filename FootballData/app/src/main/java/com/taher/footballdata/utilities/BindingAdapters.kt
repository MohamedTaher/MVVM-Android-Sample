package com.taher.footballdata.utilities

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("isGone")
fun View.bindIsGone(isGone: Boolean) {
    visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}