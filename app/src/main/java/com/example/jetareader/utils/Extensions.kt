package com.example.jetareader.utils

import android.content.Context
import android.icu.text.DateFormat
import android.widget.Toast
import com.google.firebase.Timestamp

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.screenWidth(): Float {
    val displayMetrics = this.resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}

fun String.isEmail(): Boolean {
    val emailRegex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\$")
    return emailRegex.matches(this)
}

fun Timestamp.formatDate() =
    DateFormat.getDateInstance()
        .format(this.toDate())
        .toString()
