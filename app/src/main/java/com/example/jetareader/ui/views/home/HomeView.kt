@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetareader.ui.views.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.ui.theme.Pink80
import com.example.jetareader.ui.theme.Purple80

@Composable
fun HomeView(navController: NavHostController) {
    Scaffold(
        topBar = {
            AppBar(titleResId = R.string.splash_title, navController = navController)
        }, floatingActionButton = {
            FabContent {

            }
        }) { padding ->
        Surface(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {

        }
    }
}

@Composable
private fun AppBar(titleResId: Int, navController: NavController, showProfile: Boolean = false) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (showProfile) {
                    Image(imageVector = Icons.Rounded.AccountCircle, contentDescription = "")
                    Text(
                        stringResource(id = titleResId),
                        color = Pink80,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

        }, actions = {

        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            navigationIconContentColor = Color.White
        )
    )
}

@Composable
private fun FabContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.content_fab_description),
            tint = Purple80
        )
    }
}