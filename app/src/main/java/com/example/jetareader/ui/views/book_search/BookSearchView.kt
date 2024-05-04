package com.example.jetareader.ui.views.book_search

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.ui.navigation.ViewsEnum
import com.example.jetareader.ui.widgets.AppBarWidget
import com.example.jetareader.ui.widgets.BookRow
import com.example.jetareader.ui.widgets.InputField
import com.example.jetareader.utils.showToast

@Composable
fun BookSearchView(navController: NavHostController) {
    val viewModel: BookSearchViewModel = hiltViewModel()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            AppBarWidget(
                titleResId = R.string.book_search_title,
                onBackAction = { navController.navigate(ViewsEnum.HOME.name) }
            )
        }
    ) { padding ->

        Column(modifier = Modifier.padding(padding)) {
            SearchForm {
                viewModel.searchBooks(it)
            }
            when {
                viewModel.loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                viewModel.error != null -> context.showToast(viewModel.error!!)
                else -> BookList(navController, viewModel, context)
            }

        }
    }
}

@Composable
private fun BookList(
    navController: NavHostController,
    viewModel: BookSearchViewModel,
    context: Context
) {
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(viewModel.books) { book ->
            BookRow(context, navController, book)
        }
    }
}

@Composable
private fun SearchForm(onSearch: (String) -> Unit = {}) {
    val searchQueryState = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) { searchQueryState.value.isNotEmpty() }

    InputField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        state = searchQueryState,
        labelResId = R.string.search_lbl,
        imeAction = ImeAction.Search,
        keyboardActions = KeyboardActions {
            if (!valid) return@KeyboardActions
            onSearch(searchQueryState.value.trim())
            keyboardController?.hide()
        },
    )
}
