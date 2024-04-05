package com.example.jetareader.ui.widgets

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.jetareader.R

@Composable
fun LogoWidget(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.splash_title),
        modifier = modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.displayMedium,
        color = Color.Red.copy(alpha = 0.5f)
    )
}