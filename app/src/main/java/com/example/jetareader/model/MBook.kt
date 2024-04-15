package com.example.jetareader.model

data class MBook(
    var id: String,
    var title: String,
    var authors: String,
    var notes: String? = null
)
