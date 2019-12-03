package com.taher.footballdata.utilities.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.taher.footballdata.R


internal fun FragmentManager.removeFragment(tag: String,
                                            slideIn: Int = R.anim.slide_left,
                                            slideOut: Int = R.anim.slide_right) {

    val fragment = this.findFragmentByTag(tag)
    fragment?.let {
        this.beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(slideIn, slideOut)
            .remove(it)
            .commitNow()
    }
}

internal fun FragmentManager.addFragment(containerViewId: Int,
                                         fragment: Fragment,
                                         tag: String,
                                         slideIn: Int = R.anim.slide_left,
                                         slideOut: Int = R.anim.slide_right) {

    this.beginTransaction().disallowAddToBackStack()
        .setCustomAnimations(slideIn, slideOut)
        .add(containerViewId, fragment, tag)
        .commit()
}
