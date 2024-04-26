package com.example.jetareader.repository

import android.util.Log
import com.example.jetareader.data.ResultWrapper
import com.example.jetareader.model.MBook
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

private const val TAG = "FirebaseRepositoryImp"

class FirebaseRepositoryImp @Inject constructor(private val queryBook: Query) : FirebaseRepository {

    override suspend fun getBookById(id: String): ResultWrapper<MBook> = try {
        val result = queryBook.whereEqualTo("id", id).get().await().documents.firstOrNull()
            ?.toObject(MBook::class.java)
        if (result != null) {
            ResultWrapper.Success(result)
        } else {
            ResultWrapper.Error(Exception("No book found with ID: $id"))
        }
    } catch (e: Exception) {
        Log.d(TAG, "getAllBooksByUser: ${e.message}")
        ResultWrapper.Error(e)
    }

    override suspend fun getAllBooksByUser(userId: String): ResultWrapper<List<MBook>> = try {
        val result = queryBook.get().await().documents.mapNotNull {
            it.toObject(MBook::class.java)
        }.filter {
            it.userId == userId
        }
        ResultWrapper.Success(result)
    } catch (e: Exception) {
        Log.d(TAG, "getAllBooksByUser: ${e.message}")
        ResultWrapper.Error(e)
    }

}