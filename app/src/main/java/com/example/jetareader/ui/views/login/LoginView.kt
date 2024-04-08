package com.example.jetareader.ui.views.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.ui.widgets.EmailInput
import com.example.jetareader.ui.widgets.LogoWidget
import com.example.jetareader.ui.widgets.PasswordInput
import com.example.jetareader.ui.widgets.SubmitButton

@Composable
fun LoginView(navController: NavHostController) {
    val viewModel: LoginViewModel = hiltViewModel()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LogoWidget()
            Form(viewModel = viewModel, loading = false, onDone = { email, pwd ->

            })
        }
    }
}

@Composable
private fun Form(viewModel: LoginViewModel, loading: Boolean, onDone: (String, String) -> Unit) {
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val passwordVisibilityState = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val validState = remember(emailState.value, passwordState.value) {
        viewModel.validateForm(emailState.value, passwordState.value)
    }

    Column(
        modifier = Modifier
            .height(250.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInput(
            state = emailState,
            enabled = !loading,
            keyboardActions = KeyboardActions {
                passwordFocusRequest.requestFocus()
            }
        )

        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            state = passwordState,
            enabled = !loading,
            visibility = passwordVisibilityState,
            keyboardActions = KeyboardActions {
                if (!validState) return@KeyboardActions
                onDone(emailState.value, passwordState.value)
            }
        )

        SubmitButton(
            text = stringResource(R.string.login_btn),
            enabled = !loading && validState,
            loading = loading,
            onClick = {
                onDone(emailState.value, passwordState.value)
            }
        )
    }
}