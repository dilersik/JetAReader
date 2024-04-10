package com.example.jetareader.ui.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetareader.R

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    state: MutableState<String>,
    label: String = stringResource(R.string.email_lbl),
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    InputField(
        modifier = modifier,
        state = state,
        label = label,
        enabled = enabled,
        imeAction = imeAction,
        keyboardActions = keyboardActions,
        keyboardType = KeyboardType.Email
    )
}

@Composable
fun PasswordInput(
    modifier: Modifier,
    state: MutableState<String>,
    visibility: MutableState<Boolean>,
    keyboardActions: KeyboardActions,
    label: String = stringResource(R.string.password_lbl),
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Done,
) {
    InputField(
        modifier = modifier,
        state = state,
        label = label,
        enabled = enabled,
        imeAction = imeAction,
        keyboardActions = keyboardActions,
        keyboardType = KeyboardType.Password,
        visualTransformation = if (visibility.value) VisualTransformation.None
        else PasswordVisualTransformation(),
        trailingIcon = { PasswordVisibility(visibility) }
    )
}

@Composable
private fun PasswordVisibility(visibility: MutableState<Boolean>) {
    IconButton(onClick = {
        visibility.value = !visibility.value
    }) {
        Icons.Default.Close
    }
}

@Composable
private fun InputField(
    modifier: Modifier = Modifier,
    state: MutableState<String>,
    label: String,
    enabled: Boolean,
    singleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {}
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = { state.value = it },
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        label = { Text(text = label) },
        singleLine = singleLine,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon
    )
}

@Composable
fun SubmitButton(text: String, enabled: Boolean, loading: Boolean, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = enabled,
        shape = CircleShape
    ) {
        if (loading)
            CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else
            Text(text, modifier = Modifier.padding(6.dp))
    }
}
