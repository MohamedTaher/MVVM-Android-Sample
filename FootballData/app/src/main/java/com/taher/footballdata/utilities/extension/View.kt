package com.taher.footballdata.utilities.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.snackbar.Snackbar
import com.taher.footballdata.R

fun View.showSnackBar(message: String, duration: Int) {
    Snackbar.make(this, message, duration).show()
}

fun View.showSnackBar(mContext: Context, message: String, @StringRes actionText: Int, action: () -> Any) {
    val snackBar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    snackBar.setAction(actionText) { action.invoke() }
    snackBar.setActionTextColor(
        ContextCompat.getColor(mContext, R.color.colorPrimary)
    )
    snackBar.show()
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.GONE }

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun <T : ViewDataBinding?> ViewGroup.bindingInflate(@LayoutRes layoutRes: Int): T =
    DataBindingUtil.inflate<T>(LayoutInflater.from(context), layoutRes, this, false)