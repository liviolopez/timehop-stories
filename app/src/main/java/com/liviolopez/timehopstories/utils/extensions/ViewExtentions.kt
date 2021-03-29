package com.liviolopez.timehopstories.utils.extensions

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.setGone() { visibility = View.GONE }
fun View.setVisible() { visibility = View.VISIBLE }

fun View.showSnackBar(
    msg: String,
    duration: Int = Snackbar.LENGTH_LONG,
) {
    Snackbar.make(this, msg, duration).show()
}