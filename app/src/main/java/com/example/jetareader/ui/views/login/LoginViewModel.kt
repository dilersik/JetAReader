package com.example.jetareader.ui.views.login

import androidx.lifecycle.ViewModel
import com.example.jetareader.utils.isEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    fun validateForm(email: String, pwd: String) = validateEmail(email) && validatePwd(pwd)

    private fun validateEmail(email: String) = email.trim().isNotEmpty() && email.isEmail()

    private fun validatePwd(pwd: String) = pwd.isNotEmpty()
}