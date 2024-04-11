@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetareader.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AppBarWidget(
    titleResId: Int,
    navController: NavController,
    actions: @Composable () -> Unit,
    showBackArrow: Boolean = true
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 4.dp, // Adjust the elevation value as needed
                shape = RoundedCornerShape(0.dp, 0.dp, 16.dp, 16.dp), // Adjust the shape as needed
                clip = false
            )
    ) {
        TopAppBar(
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (!showBackArrow) {
                        Image(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "",
                            modifier = Modifier.scale(1.5f)
                        )
                        Text(
                            stringResource(id = titleResId),
                            modifier = Modifier.padding(start = 16.dp),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            },
            actions = { actions() },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                navigationIconContentColor = Color.White
            )
        )
    }
}