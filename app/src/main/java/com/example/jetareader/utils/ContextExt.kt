package com.example.jetareader.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.screenWidth(): Float {
    val displayMetrics = this.resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}