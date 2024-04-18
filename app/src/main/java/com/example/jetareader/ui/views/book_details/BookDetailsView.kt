package com.example.jetareader.ui.views.book_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.data.ResultWrapper
import com.example.jetareader.model.Book
import com.example.jetareader.ui.widgets.AppBarWidget
import com.example.jetareader.utils.showToast

@Composable
fun BookDetailsView(navController: NavHostController, navBackStackEntry: NavBackStackEntry) {
    val context = LocalContext.current
    val viewModel: BookDetailsViewModel = hiltViewModel()
    val bookId = navBackStackEntry.arguments?.getString(BookDetailsViewConstants.PARAM) ?: ""
    val resultWrapper = produceState<ResultWrapper<Book>>(initialValue = ResultWrapper.Loading()) {
        value = viewModel.getBookInfo(bookId)
    }.value

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

            when (resultWrapper) {
                is ResultWrapper.Loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                is ResultWrapper.Success -> Text(text = resultWrapper.data.volumeInfo.title)
                is ResultWrapper.Error -> context.showToast(resultWrapper.exception.message.toString())
            }

        }
    }
}

object BookDetailsViewConstants {
    const val PARAM = "bookId"
}