package com.example.jetareader.ui.views.book_search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.ui.widgets.AppBarWidget
import com.example.jetareader.ui.widgets.FabWidget
import com.example.jetareader.ui.widgets.InputField

@Composable
fun BookSearchView(navController: NavHostController) {
    Scaffold(
        topBar = {
            AppBarWidget(
                titleResId = R.string.book_search_title,
                navController = navController,
            )
        }, floatingActionButton = {
            FabWidget {

            }
        }) { padding ->

        Surface(modifier = Modifier.padding(padding)) {
            SearchForm() {

            }
        }
    }
}

@Composable
private fun SearchForm(onSearch: (String) -> Unit = {}) {
    val searchQueryState = rememberSaveable { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(searchQueryState.value) { searchQueryState.value.isNotEmpty() }
    Column {
        InputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            state = searchQueryState,
            labelResId = R.string.search_lbl,
            keyboardActions = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                keyboardController?.hide()
            })
    }
}