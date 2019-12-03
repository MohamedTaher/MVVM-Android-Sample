package com.taher.footballdata.utilities.extension

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat

val Context.networkInfo: NetworkInfo? get() =
    (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

fun Context.makeToast(name: Int) {
    val msg = resources?.getString(name)
    makeToast(msg)
}

fun Context.makeToast(msg: String?) {
    val mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    mToast.show()
}

inline fun <reified T : Activity> Activity.navigate(id: String, sharedView: View? = null,
                                                    transitionName: String? = null) {
    val intent = Intent(this, T::class.java)
    intent.putExtra("id", id)

    var options: ActivityOptionsCompat? = null

    if (sharedView != null && transitionName != null) {
        options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, sharedView, transitionName)
    }

    ActivityCompat.startActivity(this, intent, options?.toBundle())
}

fun Activity.getNavigationId(): String {
    val intent = intent
    return intent.getStringExtra("id")
}