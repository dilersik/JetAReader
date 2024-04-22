package com.example.jetareader.repository

import com.example.jetareader.data.ResultWrapper
import com.example.jetareader.model.MBook
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImp @Inject constructor(private val queryBook: Query) : FirebaseRepository {

    override suspend fun getAllBooks(): ResultWrapper<List<MBook>> = try {
        val result = queryBook.get().await().documents.mapNotNull {
           it.toObject(MBook::class.java)
        }
        ResultWrapper.Success(result)
    } catch (e: Exception) {
        ResultWrapper.Error(e)
    }

}