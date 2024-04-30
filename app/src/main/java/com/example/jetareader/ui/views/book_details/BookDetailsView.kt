package com.example.jetareader.ui.views.book_details

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.jetareader.R
import com.example.jetareader.data.ResultWrapper
import com.example.jetareader.model.Book
import com.example.jetareader.ui.theme.TitleColor
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
    val error = viewModel.error.observeAsState().value
    if (error != null) {
        context.showToast(error)
    }

    Scaffold(
        topBar = {
            AppBarWidget(
                titleResId = R.string.book_details_title,
                onBackAction = { navController.popBackStack() },
            )
        }
    ) { padding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = padding.calculateTopPadding() + 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                when (resultWrapper) {
                    is ResultWrapper.Loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                    is ResultWrapper.Success -> ShowBookDetails(
                        resultWrapper.data,
                        context,
                        viewModel,
                        navController
                    )

                    is ResultWrapper.Error -> context.showToast(resultWrapper.exception.message.toString())
                }
            }
        }
    }
}

@Composable
private fun ShowBookDetails(
    book: Book,
    context: Context,
    viewModel: BookDetailsViewModel,
    navController: NavController
) {
    val description = buildAnnotatedString {
        append(HtmlCompat.fromHtml(book.volumeInfo.description, HtmlCompat.FROM_HTML_MODE_COMPACT))
    }

    Card(elevation = CardDefaults.cardElevation(4.dp)) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context)
                .data(book.volumeInfo.imageLinks?.large)
                .crossfade(true)
                .size(Size.ORIGINAL) // Important in order to work with verticalScroll
                .build()
        )
        Image(
            painter = painter,
            contentDescription = "",
        )
    }

    Row(
        modifier = Modifier
            .padding(top = 20.dp, bottom = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(.9f)
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(book.volumeInfo.infoLink))
                    context.startActivity(intent)
                },
            text = book.volumeInfo.title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TitleColor,
            textAlign = TextAlign.Center
        )

        Icon(
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = "",
            modifier = Modifier
                .weight(.1f)
                .size(40.dp)
                .clickable {
                    viewModel.saveBookToFirebase(book) {
                        navController.popBackStack()
                    }
                })
    }

    Text(
        text = book.volumeInfo.subtitle ?: "",
        style = MaterialTheme.typography.titleLarge,
    )
    Text(
        text = stringResource(
            R.string.book_date_lbl,
            book.volumeInfo.publishedDate
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Left
    )
    Text(
        text = stringResource(
            R.string.book_publisher_lbl,
            book.volumeInfo.publisher
        ),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Left
    )
    Text(
        text = stringResource(
            R.string.book_author_lbl,
            book.volumeInfo.authors
        ),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Left
    )
    Text(
        text = description,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Left
    )
}

object BookDetailsViewConstants {
    const val PARAM = "bookId"
}