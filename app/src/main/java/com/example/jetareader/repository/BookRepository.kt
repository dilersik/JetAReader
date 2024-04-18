package com.example.jetareader.repository

import com.example.jetareader.data.ResultWrapper
import com.example.jetareader.model.Book

interface BookRepository {
    suspend fun searchBooks(query: String): ResultWrapper<List<Book>>
    suspend fun getBookById(id: String): ResultWrapper<Book>
}