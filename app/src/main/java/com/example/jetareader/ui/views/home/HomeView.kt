@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.jetareader.ui.views.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.model.Book
import com.example.jetareader.ui.navigation.ViewsEnum
import com.example.jetareader.ui.theme.Purple80
import com.example.jetareader.ui.views.login.LoginViewModel
import com.example.jetareader.ui.widgets.AppBarWidget

@Composable
fun HomeView(navController: NavHostController) {
    val loginViewModel: LoginViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            AppBarWidget(
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
        HomeContent(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            navController = navController
        )
    }
}

@Composable
private fun HomeContent(modifier: Modifier = Modifier, navController: NavController) {
    Surface(modifier) {
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            Row(modifier = Modifier.align(alignment = Alignment.Start)) {
                TitleSection(labelResId = R.string.home_title_section)

            }
        }
    }
}

@Composable
private fun TitleSection(labelResId: Int) {
    Text(
        text = stringResource(labelResId),
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun ReadingBooks(books: List<Book>, navController: NavController) {

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