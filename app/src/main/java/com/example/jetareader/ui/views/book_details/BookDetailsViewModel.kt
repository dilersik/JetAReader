package com.example.jetareader.ui.views.book_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jetareader.model.Book
import com.example.jetareader.model.MBook
import com.example.jetareader.repository.BookRepository
import com.example.jetareader.utils.Constants
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailsViewModel @Inject constructor(private val bookRepository: BookRepository) :
    ViewModel() {

    private val _error: MutableLiveData<String?> = MutableLiveData(null)
    val error = _error

    suspend fun getBookInfo(bookId: String) = bookRepository.getBookById(bookId)

    fun saveBookToFirebase(book: Book, redirect: () -> Unit) {
        val auth = Firebase.auth
        val firestore = FirebaseFirestore.getInstance()
        val mBook = MBook(
            title = book.volumeInfo.title,
            authors = book.volumeInfo.authors.toString(),
            userId = auth.currentUser?.uid.toString(),
            googleBookId = book.id,
            photoUrl = book.volumeInfo.imageLinks?.medium,
            categories = book.volumeInfo.categories.toString(),
            publishedDate = book.volumeInfo.publishedDate,
            description = book.volumeInfo.description,
            pageCount = book.volumeInfo.pageCount
        )
        val dbCollection = firestore.collection(Constants.FirebaseCollections.BOOKS)
        dbCollection.add(mBook)
            .addOnSuccessListener { reference ->
                dbCollection.document(reference.id)
                    .update(hashMapOf("id" to reference.id) as Map<String, Any>)
                    .addOnSuccessListener {
                        redirect()
                    }
                    .addOnFailureListener {
                        _error.value = it.message
                    }
            }
            .addOnFailureListener {
                _error.value = it.message
            }
    }

}