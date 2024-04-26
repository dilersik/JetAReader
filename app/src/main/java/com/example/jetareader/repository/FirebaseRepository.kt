package com.example.jetareader.repository

import com.example.jetareader.data.ResultWrapper
import com.example.jetareader.model.MBook

interface FirebaseRepository {
    suspend fun getBookById(id: String): ResultWrapper<MBook>
    suspend fun getAllBooksByUser(userId: String): ResultWrapper<List<MBook>>
}