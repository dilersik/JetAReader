package com.example.jetareader.ui.views.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetareader.model.LoadingState
import com.example.jetareader.utils.isEmail
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
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

    private val _error: MutableStateFlow<String?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    private val auth: FirebaseAuth = Firebase.auth

    fun createFirebaseUser(email: String, pwd: String) = try {

    } catch (e: Exception) {

    }

    fun signInFirebaseUser(email: String, pwd: String, redirect: () -> Unit) = viewModelScope.launch {
        _loading.value = true
        try {
            auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        redirect()
                    } else {
                        _error.value = task.exception?.message
                    }
                    _loading.value = false
                }
        } catch (e: Exception) {
            Log.e(TAG, "signInFirebaseUser:" + e.message)
            _loading.value = false
            _error.value = e.message
        }
    }

    fun validateForm(email: String, pwd: String) = validateEmail(email) && validatePwd(pwd)

    private fun validateEmail(email: String) = email.trim().isNotEmpty() && email.isEmail()

    private fun validatePwd(pwd: String) = pwd.isNotEmpty()

    companion object {
        private const val TAG = "LoginViewModel"
    }
}