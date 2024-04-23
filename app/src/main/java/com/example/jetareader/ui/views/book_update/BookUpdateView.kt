package com.example.jetareader.ui.views.book_update

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.ui.views.book_update.BookUpdateViewConstants.PARAM
import com.example.jetareader.ui.views.home.HomeViewModel
import com.example.jetareader.ui.widgets.AppBarWidget

@Composable
fun BookUpdateView(navController: NavHostController, navBackStackEntry: NavBackStackEntry) {
    val bookId = navBackStackEntry.arguments?.getString(PARAM) ?: ""
    val viewModel: HomeViewModel = hiltViewModel()
    val book = viewModel.getBookById(bookId)

    Scaffold(
        topBar = {
            AppBarWidget(
                titleResId = R.string.book_update_title,
                navController = navController,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = book?.title.toString())
        }
    }
}

object BookUpdateViewConstants {
    const val PARAM = "bookId"
}