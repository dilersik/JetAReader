package com.example.jetareader.network

import com.example.jetareader.model.Book
import com.example.jetareader.model.BookList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {

    @GET("volumes")
    suspend fun getVolumes(@Query("q") query: String): BookList

    @GET("volumes/{bookId}")
    suspend fun getVolumeByBookId(@Path("bookId") bookId: String): Book

}