package com.example.jetareader.ui.views.home

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.model.MBook
import com.example.jetareader.ui.navigation.ViewsEnum
import com.example.jetareader.ui.theme.Purple40
import com.example.jetareader.ui.widgets.AppBarWidget
import com.example.jetareader.ui.widgets.FabWidget
import com.example.jetareader.utils.showToast

@Composable
fun HomeView(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            AppBarWidget(
                titleResId = R.string.splash_title,
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.logout().run {
                                navController.navigate(ViewsEnum.LOGIN.name)
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = stringResource(R.string.content_logout_description),
                        )
                    }
                },
                showBackArrow = false,
            )
        },
        floatingActionButton = {
            FabWidget { navController.navigate(ViewsEnum.BOOK_SEARCH.name) }
        }) { padding ->
        HomeContent(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            navController = navController,
            viewModel = viewModel,
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel
) {
    Column(modifier.verticalScroll(ScrollState(0))) {
        ReadingActivityRow(navController, viewModel)

        TitleSection(
            labelResId = R.string.home_subtitle_section,
            modifier = Modifier.padding(top = 16.dp)
        )
        when {
            viewModel.loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            viewModel.error != null -> LocalContext.current.showToast(viewModel.error!!)
            else -> WishlistBooks(
                books = viewModel.getWishlistBooks(),
                navController = navController
            )
        }
    }
}

@Composable
private fun ReadingActivityRow(navController: NavController, viewModel: HomeViewModel) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        TitleSection(
            labelResId = R.string.home_title_section,
            modifier = Modifier.weight(.8f),
        )
        Column(
            modifier = Modifier
                .weight(.2f)
                .padding(top = 16.dp, end = 16.dp)
                .clickable {
                    navController.navigate(ViewsEnum.BOOK_STATS.name)
                },
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = "",
                modifier = Modifier.size(50.dp),
                tint = MaterialTheme.colorScheme.secondary,
            )
            Text(
                text = viewModel.getUser()?.displayName.toString(),
                modifier = Modifier.padding(2.dp),
                style = MaterialTheme.typography.labelLarge,
                color = Purple40,
                maxLines = 1,
                overflow = TextOverflow.Clip,
            )
            HorizontalDivider()
        }
    }

    Row(modifier = Modifier.padding(top = 16.dp)) {
        val books = viewModel.getReadingBooks()
        when {
            viewModel.loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            books.isEmpty() -> Text(
                text = stringResource(R.string.no_books_msg),
                style = MaterialTheme.typography.bodyLarge
            )

            else -> {
                books.forEach { book ->
                    BookCardItem(book) {
                        navController.navigate(ViewsEnum.BOOK_UPDATE.name + "/${book.id}")
                    }
                }
            }
        }
    }
}

@Composable
private fun TitleSection(labelResId: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(labelResId),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.padding(horizontal = 16.dp)
    )
}

@Composable
private fun WishlistBooks(books: List<MBook>, navController: NavController) {
    val scrollState = rememberScrollState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .padding(top = 16.dp)
            .horizontalScroll(scrollState)
    ) {
        if (books.isEmpty()) {
            Text(
                text = stringResource(R.string.no_books_msg),
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            books.forEach { mBook ->
                BookCardItem(mBook = mBook) {
                    navController.navigate(ViewsEnum.BOOK_UPDATE.name + "/${mBook.id}")
                }
            }
        }
    }
}
