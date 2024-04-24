package com.example.jetareader.ui.views.book_update

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.jetareader.R
import com.example.jetareader.model.MBook
import com.example.jetareader.ui.views.book_update.BookUpdateViewConstants.PARAM
import com.example.jetareader.ui.views.home.HomeViewModel
import com.example.jetareader.ui.widgets.AppBarWidget
import com.example.jetareader.ui.widgets.InputField

@Composable
fun BookUpdateView(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry,
) {
    val bookId = navBackStackEntry.arguments?.getString(PARAM) ?: ""
    val viewModel: HomeViewModel = hiltViewModel()
    val mBook = viewModel.getBookById(bookId)

    Scaffold(
        topBar = {
            AppBarWidget(
                titleResId = R.string.book_update_title,
                navController = navController,
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(ScrollState(0)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            mBook?.let {
                CardBook(it)
                FormBook(it)
            }
        }
    }
}

@Composable
private fun FormBook(mBook: MBook) {
    val textFieldValue = rememberSaveable { mutableStateOf(mBook.notes ?: "") }
    InputField(
        modifier = Modifier
            .height(140.dp)
            .padding(20.dp),
        state = textFieldValue,
        singleLine = false,
        labelResId = R.string.enter_your_thoughts_lbl,
        imeAction = ImeAction.Default,
    )
}

@Composable
private fun CardBook(book: MBook) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 8.dp)
            .clickable {},
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(8.dp),
    ) {
        Row {
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.photoUrl).crossfade(true)
                    .size(Size.ORIGINAL) // Important in order to work with verticalScroll
                    .build(),
            )
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier.height(140.dp),
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                )
                Text(
                    text = stringResource(
                        id = R.string.book_author_lbl,
                        book.authors,
                    ),
                    modifier = Modifier.padding(top = 10.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 2,
                )
                Text(
                    text = stringResource(
                        id = R.string.book_date_lbl,
                        book.publishedDate.toString(),
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

object BookUpdateViewConstants {
    const val PARAM = "bookId"
}
