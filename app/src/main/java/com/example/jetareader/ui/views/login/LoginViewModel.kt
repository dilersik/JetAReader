package com.example.jetareader.ui.views.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetareader.model.MUser
import com.example.jetareader.utils.isEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error: MutableLiveData<String?> = MutableLiveData(null)
    val error = _error

    private val auth: FirebaseAuth = Firebase.auth

    fun createFirebaseUser(email: String, pwd: String, redirect: () -> Unit) =
        viewModelScope.launch {
            _loading.value = true
            try {
                auth.createUserWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener { task ->
                        val displayName = email.substringBefore("@")
                        if (task.isSuccessful) {
                            createFirebaseFirestoreUser(displayName, redirect)
                        } else {
                            Log.e(TAG, task.exception.toString())
                            _error.value = task.exception?.message
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                _loading.value = false
                _error.value = e.message
            }
        }

    private fun createFirebaseFirestoreUser(displayName: String?, redirect: () -> Unit) {
        val user = MUser(
            userId = auth.currentUser?.uid.toString(),
            displayName = displayName.toString()
        ).mapToFirestore()

        FirebaseFirestore.getInstance()
            .collection("users")
            .add(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    redirect()
                } else {
                    Log.e(TAG, task.exception.toString())
                    _error.value = task.exception?.message
                }
                _loading.value = false
            }
    }

    fun signInFirebaseUser(email: String, pwd: String, redirect: () -> Unit) =
        viewModelScope.launch {
            _loading.value = true
            try {
                auth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            redirect()
                        } else {
                            Log.e(TAG, task.exception.toString())
                            _error.value = task.exception?.message
                        }
                        _loading.value = false
                    }
            } catch (e: Exception) {
                Log.e(TAG, e.message.toString())
                _loading.value = false
                _error.value = e.message
            }
        }

    fun isFBUserAuth() = auth.currentUser?.email.isNullOrEmpty().not()

    fun validateForm(email: String, pwd: String) = validateEmail(email) && validatePwd(pwd)

    private fun validateEmail(email: String) = email.trim().isNotEmpty() && email.isEmail()

    private fun validatePwd(pwd: String) = pwd.length >= 6

    companion object {
        private const val TAG = "LoginViewModel"
    }
}