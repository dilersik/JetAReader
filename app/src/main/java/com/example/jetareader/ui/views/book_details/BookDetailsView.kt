package com.example.jetareader.ui.views.book_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.ui.widgets.AppBarWidget

@Composable
fun BookDetailsView(navController: NavHostController, navBackStackEntry: NavBackStackEntry) {
    val bookId = navBackStackEntry.arguments?.getString(BookDetailsViewConstants.PARAM) ?: ""
    Scaffold(
        topBar = {
            AppBarWidget(
                titleResId = R.string.book_details_title,
                navController = navController,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = bookId)
        }
    }
}

object BookDetailsViewConstants {
    const val PARAM = "bookId"
}