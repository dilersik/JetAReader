package com.example.jetareader.ui.views.book_details

import androidx.lifecycle.ViewModel
import com.example.jetareader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(private val bookRepository: BookRepository) :
    ViewModel() {

    suspend fun getBookInfo(bookId: String) = bookRepository.getBookById(bookId)

}