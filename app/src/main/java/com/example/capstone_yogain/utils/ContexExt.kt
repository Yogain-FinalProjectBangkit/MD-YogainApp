package com.example.capstone_yogain.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun showToast(context: Context, text: Int) {
    Toast.makeText(context, context.getString(text), Toast.LENGTH_SHORT).show()
}


fun View.gone() {
    visibility = View.GONE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.disable () {
    isEnabled = false
}

fun View.enable() {
    isEnabled = true
}