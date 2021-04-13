package com.liviolopez.timehopstories.utils.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.setGone() { visibility = View.GONE }
fun View.setVisible() { visibility = View.VISIBLE }

inline fun <T : View> T.visibleIf(isTrue: (T) -> Boolean) {
    if (isTrue(this))
        this.setVisible()
    else
        this.setGone()
}

fun View.showSnackBar(
    msg: String,
    duration: Int = Snackbar.LENGTH_LONG,
) {
    Snackbar.make(this, msg, duration).show()
}
