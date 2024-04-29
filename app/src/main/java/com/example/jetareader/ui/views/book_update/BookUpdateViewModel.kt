package com.example.jetareader.ui.views.book_update

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetareader.data.ResultWrapper
import com.example.jetareader.model.MBook
import com.example.jetareader.repository.FirebaseRepository
import com.example.jetareader.utils.Constants
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookUpdateViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    private val _error: MutableState<String?> = mutableStateOf(null)
    val error by _error

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading by _loading

    private val _book: MutableState<MBook> = mutableStateOf(MBook())
    val book by _book


    fun getBookById(id: String) {
        viewModelScope.launch {
            _loading.value = true
            when (val result = firebaseRepository.getBookById(id)) {
                is ResultWrapper.Success -> _book.value = result.data
                is ResultWrapper.Error -> _error.value = result.exception.message
                else -> {}
            }
            _loading.value = false
        }
    }

    fun updateBook(mBook: MBook, isFinished: Boolean, isStarted: Boolean, resultCall: () -> Unit) {
        mBook.finishedReading = if (isFinished) Timestamp.now() else mBook.finishedReading
        mBook.startedReading = if (isStarted) Timestamp.now() else mBook.startedReading
        mBook.id?.let {
            FirebaseFirestore.getInstance()
                .collection(Constants.FirebaseCollections.BOOKS)
                .document(it)
                .update(mBook.mapToFirestore())
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        resultCall()
                    } else {
                        _error.value = task.exception?.message
                    }
                }
        }
    }

}