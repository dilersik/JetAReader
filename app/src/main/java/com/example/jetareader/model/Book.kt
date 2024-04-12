package com.example.jetareader.model

data class Book(
    var id: String,
    var title: String,
    var authors: String,
    var notes: String? = null
)
