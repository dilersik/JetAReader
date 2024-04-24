package com.example.jetareader.ui.views.book_search

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.jetareader.R
import com.example.jetareader.model.Book
import com.example.jetareader.ui.navigation.ViewsEnum
import com.example.jetareader.ui.widgets.AppBarWidget
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
                navController = navController,
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
            BoxWithConstraints {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = maxHeight)
                        .padding(bottom = 16.dp)
                        .clickable {
                            navController.navigate(ViewsEnum.BOOK_DETAILS.name + "/${book.id}")
                        },
                    shape = RectangleShape,
                    elevation = CardDefaults.cardElevation(7.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                ) {
                    BookRow(context, book)
                }
            }
        }
    }
}

@Composable
private fun BookRow(context: Context, book: Book) {
    Row(modifier = Modifier.padding(8.dp)) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(book.volumeInfo.imageLinks?.thumbnail)
                .crossfade(true)
                .scale(Scale.FILL)
                .build(),
        )
        Image(
            painter = painter,
            contentDescription = "",
            modifier = Modifier
                .width(100.dp)
                .height(140.dp),
        )

        Column(modifier = Modifier.padding(start = 16.dp)) {
            Text(
                text = book.volumeInfo.title,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 2,
            )
            Text(
                text = book.volumeInfo.subtitle ?: "",
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.titleSmall,
                maxLines = 2,
            )
            Text(
                text = stringResource(
                    R.string.book_date_lbl,
                    book.volumeInfo.publishedDate,
                ),
                modifier = Modifier.padding(top = 6.dp),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
            Text(
                text = stringResource(
                    R.string.book_author_lbl,
                    book.volumeInfo.authors,
                ),
                overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
            )
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
