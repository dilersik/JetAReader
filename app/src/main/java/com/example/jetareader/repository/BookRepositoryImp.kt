package com.example.jetareader.repository

import android.util.Log
import com.example.jetareader.data.ResultWrapper
import com.example.jetareader.model.Book
import com.example.jetareader.network.BooksApi
import javax.inject.Inject

class BookRepositoryImp @Inject constructor(private val booksApi: BooksApi
) : BookRepository {

    override suspend fun searchBooks(query: String): ResultWrapper<List<Book>> = try {
        val volumes = booksApi.getVolumes(query)
        ResultWrapper.Success(volumes.books)
    } catch (e: Exception) {
        Log.e(TAG, "searchBooks: ${e.localizedMessage}")
        ResultWrapper.Error(e)
    }

    override suspend fun getBookById(id: String): ResultWrapper<Book> = try {
        ResultWrapper.Success(booksApi.getVolumeByBookId(id))
    } catch (e: Exception) {
        Log.e(TAG, "getBookById: ${e.localizedMessage}")
        ResultWrapper.Error(e)
    }

    private companion object {
        private const val TAG = "BookRepositoryImp"
    }
}