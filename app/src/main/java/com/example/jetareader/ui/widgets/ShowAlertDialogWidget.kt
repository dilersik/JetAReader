package com.example.jetareader.ui.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.jetareader.R

@Composable
fun ShowAlertDialogWidget(
    titleResId: Int,
    textResId: Int,
    openDialog: MutableState<Boolean>,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit = {},
) {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { dismissDialog(openDialog, onDismiss) },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(text = stringResource(id = R.string.alert_btn_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { dismissDialog(openDialog, onDismiss) }) {
                    Text(text = stringResource(id = R.string.alert_btn_cancel))
                }
            },
            title = { Text(text = stringResource(id = titleResId)) },
            text = { Text(text = stringResource(id = textResId)) },
        )
    }
}

private fun dismissDialog(openDialog: MutableState<Boolean>, onDismiss: () -> Unit) {
    openDialog.value = false
    onDismiss()
}
