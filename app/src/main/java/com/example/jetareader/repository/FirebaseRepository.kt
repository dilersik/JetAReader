package com.example.jetareader.repository

import com.example.jetareader.data.ResultWrapper
import com.example.jetareader.model.MBook

interface FirebaseRepository {
    suspend fun getAllBooks(): ResultWrapper<List<MBook>>
}