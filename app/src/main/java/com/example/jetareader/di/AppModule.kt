package com.example.jetareader.di

import com.example.jetareader.network.BooksApi
import com.example.jetareader.repository.BookRepository
import com.example.jetareader.repository.BookRepositoryImp
import com.example.jetareader.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBooksApi(): BooksApi = Retrofit.Builder()
        .baseUrl(Constants.BASE_BOOKS_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BooksApi::class.java)

    @Provides
    @Singleton
    fun provideBookRepository(booksApi: BooksApi): BookRepository = BookRepositoryImp(booksApi)

}