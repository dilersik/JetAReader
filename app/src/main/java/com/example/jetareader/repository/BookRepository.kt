package com.example.jetareader.repository

import com.example.jetareader.model.Book
import com.example.jetareader.model.ResultWrapper

interface BookRepository {
    suspend fun searchBooks(query: String): ResultWrapper<List<Book>>
    suspend fun getBookById(id: String): ResultWrapper<Book>
}