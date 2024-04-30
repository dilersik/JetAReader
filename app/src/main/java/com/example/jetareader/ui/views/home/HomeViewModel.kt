package com.example.jetareader.ui.views.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetareader.data.ResultWrapper
import com.example.jetareader.model.MBook
import com.example.jetareader.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    private val _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading by _loading

    private val _error: MutableState<String?> = mutableStateOf(null)
    val error by _error

    private val _books: MutableState<List<MBook>> = mutableStateOf(emptyList())
    val books by _books

    private val auth: FirebaseAuth = Firebase.auth

    init {
        viewModelScope.launch {
            _loading.value = true
            when (val result =
                firebaseRepository.getAllBooksByUser(auth.currentUser?.uid.toString())) {
                is ResultWrapper.Success -> _books.value = result.data
                is ResultWrapper.Error -> _error.value = result.exception.message
                else -> {}
            }
            _loading.value = false
        }
    }

    fun getWishlistBooks() = books.filter { it.startedReading == null && it.finishedReading == null }

    fun getReadingBooks() = books.filter { it.startedReading != null && it.finishedReading == null }

    fun logout() = auth.signOut()

    fun getUser() = auth.currentUser

}