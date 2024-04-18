package com.example.jetareader.ui.views.book_search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetareader.data.ResultWrapper
import com.example.jetareader.model.Book
import com.example.jetareader.repository.BookRepository
import com.example.jetareader.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val bookRepository: BookRepository) :
    ViewModel() {

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading by _loading

    private val _error: MutableState<String?> = mutableStateOf(null)
    val error by _error

    private val _books: MutableState<List<Book>> = mutableStateOf(emptyList())
    val books by _books

    init {
        searchBooks(Constants.DEFAULT_SEARCH_QUERY)
    }

    fun searchBooks(query: String) = viewModelScope.launch {
        if (query.isEmpty()) return@launch
        _loading.value = true
        when (val result = bookRepository.searchBooks(query)) {
            is ResultWrapper.Success -> _books.value = result.data
            is ResultWrapper.Error -> _error.value = result.exception.message
            is ResultWrapper.Loading -> {}
        }
        _loading.value = false
    }

}