package com.example.jetareader.ui.views.book_search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetareader.model.Book
import com.example.jetareader.model.ResultWrapper
import com.example.jetareader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val bookRepository: BookRepository) :
    ViewModel() {

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading = _loading.value

    private val _error: MutableState<String?> = mutableStateOf(null)
    val error = _error.value

    private val _books: MutableState<List<Book>> = mutableStateOf(emptyList())

    fun searchBooks(query: String) = viewModelScope.launch {
        if (query.isEmpty()) return@launch
        _loading.value = true
        when (val result = bookRepository.searchBooks(query)) {
            is ResultWrapper.Success -> _books.value = result.data
            is ResultWrapper.Error -> _error.value = result.exception.message
        }
        _loading.value = false
    }

}