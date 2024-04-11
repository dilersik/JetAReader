@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetareader.ui.views.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.ui.navigation.ViewsEnum
import com.example.jetareader.ui.theme.Pink80
import com.example.jetareader.ui.theme.Purple80
import com.example.jetareader.ui.views.login.LoginViewModel

@Composable
fun HomeView(navController: NavHostController) {
    val loginViewModel: LoginViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            AppBar(
                titleResId = R.string.splash_title,
                navController = navController,
                actions = {
                    IconButton(
                        onClick = {
                            loginViewModel.logout().run {
                                navController.navigate(ViewsEnum.LOGIN.name)
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = stringResource(R.string.content_logout_description)
                        )
                    }
                },
                showBackArrow = false
            )
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
private fun AppBar(
    titleResId: Int,
    navController: NavController,
    actions: @Composable () -> Unit,
    showBackArrow: Boolean = true
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!showBackArrow) {
                    Image(
                        imageVector = Icons.Rounded.AccountCircle,
                        contentDescription = "",
                        modifier = Modifier.scale(1.5f)
                    )
                    Text(
                        stringResource(id = titleResId),
                        modifier = Modifier.padding(start = 16.dp),
                        color = Pink80,
                        style = MaterialTheme.typography.titleLarge
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