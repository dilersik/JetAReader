package com.example.jetareader.ui.views.book_search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.jetareader.R
import com.example.jetareader.model.Book
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
            BookList(navController)
        }
    }
}

@Composable
private fun BookList(navController: NavHostController) {
    val context = LocalContext.current
    val list = listOf(Book("1", "Book", "asdasd da slva"), Book("1", "Book", "asdasd da slva"))
    LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp)) {
        items(list) { book ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(bottom = 16.dp),
                shape = RectangleShape,
                elevation = CardDefaults.cardElevation(7.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Row(modifier = Modifier.padding(8.dp)) {
                    val painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(context)
                            .data("https://books.google.com/books/content?id=6DQACwAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api")
                            .crossfade(true)
                            .scale(Scale.FIT)
                            .build()
                    )
                    Image(
                        painter = painter,
                        contentDescription = "",
                        modifier = Modifier.height(200.dp)
                    )

                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text(
                            text = book.title,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Text(
                            text = stringResource(R.string.book_author_lbl, book.authors),
                            overflow = TextOverflow.Clip,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                }
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