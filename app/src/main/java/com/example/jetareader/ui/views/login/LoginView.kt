package com.example.jetareader.ui.views.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.ui.navigation.ViewsEnum
import com.example.jetareader.ui.widgets.EmailInput
import com.example.jetareader.ui.widgets.LogoWidget
import com.example.jetareader.ui.widgets.PasswordInput
import com.example.jetareader.ui.widgets.SubmitButton
import com.example.jetareader.utils.showToast

@Composable
fun LoginView(navController: NavHostController) {
    val viewModel: LoginViewModel = hiltViewModel()
    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    val context = LocalContext.current
    val error = viewModel.error.collectAsState().value

    if (error != null) {
        context.showToast(error)
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LogoWidget()
            if (showLoginForm.value) {
                UserForm(
                    viewModel = viewModel,
                    showLoginForm = showLoginForm,
                    onDone = { email, pwd ->
                        viewModel.signInFirebaseUser(email, pwd, redirect = {
                            navController.navigate(ViewsEnum.HOME.name)
                        })
                    })
            } else {
                UserForm(
                    viewModel = viewModel,
                    showLoginForm = showLoginForm,
                    onDone = { email, pwd ->
                        viewModel.createFirebaseUser(email, pwd)
                    })
            }
        }
    }
}

@Composable
private fun UserForm(
    viewModel: LoginViewModel,
    showLoginForm: MutableState<Boolean>,
    onDone: (String, String) -> Unit
) {
    val loading = viewModel.loading.collectAsState()
    val emailState = rememberSaveable { mutableStateOf("") }
    val passwordState = rememberSaveable { mutableStateOf("") }
    val passwordVisibilityState = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val validState = remember(emailState.value, passwordState.value) {
        viewModel.validateForm(emailState.value, passwordState.value)
    }
    val message = if (showLoginForm.value) {
        Triple(
            stringResource(R.string.login_btn),
            stringResource(R.string.new_user_lbl),
            stringResource(R.string.sign_up_lbl)
        )
    } else {
        Triple(
            stringResource(R.string.create_account_btn),
            stringResource(R.string.already_have_an_account_link),
            stringResource(R.string.login_link)
        )
    }

    Column(
        modifier = Modifier
            .height(250.dp)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!showLoginForm.value) {
            Text(
                text = stringResource(R.string.create_account_info),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

        EmailInput(
            state = emailState,
            enabled = !loading.value,
            keyboardActions = KeyboardActions {
                passwordFocusRequest.requestFocus()
            }
        )

        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            state = passwordState,
            enabled = !loading.value,
            visibility = passwordVisibilityState,
            keyboardActions = KeyboardActions {
                if (!validState) return@KeyboardActions
                onDone(emailState.value, passwordState.value)
            }
        )

        SubmitButton(
            text = message.first,
            enabled = !loading.value && validState,
            loading = loading.value,
            onClick = {
                onDone(emailState.value, passwordState.value)
                keyboardController?.hide()
            }
        )

        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = message.second)
            Text(
                text = message.third,
                modifier = Modifier
                    .clickable { showLoginForm.value = !showLoginForm.value }
                    .padding(5.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}