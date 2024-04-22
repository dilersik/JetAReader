package com.example.jetareader.ui.views.home

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

    fun logout() = auth.signOut()

    fun getUser() = auth.currentUser

}