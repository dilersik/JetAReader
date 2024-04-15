package com.example.jetareader.model

import com.google.gson.annotations.SerializedName

data class BookList(
    @SerializedName("items")
    val books: List<Book>,
    val kind: String,
    val totalItems: Int
)