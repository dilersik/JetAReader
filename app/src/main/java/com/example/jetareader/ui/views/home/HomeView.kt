package com.example.jetareader.ui.views.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.jetareader.R
import com.example.jetareader.model.Book
import com.example.jetareader.ui.navigation.ViewsEnum
import com.example.jetareader.ui.theme.Pink40
import com.example.jetareader.ui.theme.Purple40
import com.example.jetareader.ui.theme.Purple80
import com.example.jetareader.ui.views.login.LoginViewModel
import com.example.jetareader.ui.widgets.AppBarWidget

@Composable
fun HomeView(navController: NavHostController) {
    val loginViewModel: LoginViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            AppBarWidget(
                titleResId = R.string.splash_title,
                navController = navController,
                actions = {
                    IconButton(
                        onClick = {
                            loginViewModel.logout().run {
                                navController.navigate(ViewsEnum.LOGIN.name)
                            }
                        }) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = stringResource(R.string.content_logout_description)
                        )
                    }
                },
                showBackArrow = false
            )
        }, floatingActionButton = {
            FabContent {

            }
        }) { padding ->
        HomeContent(
            modifier = Modifier
                .padding(
                    top = padding
                        .calculateTopPadding()
                        .plus(16.dp),
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                )
                .fillMaxSize(),
            navController = navController,
            loginViewModel = loginViewModel
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    Column(modifier) {
        Row {
            TitleSection(labelResId = R.string.home_title_section, modifier = Modifier.weight(.8f))
            Column(
                modifier = Modifier.weight(.2f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Rounded.AccountCircle, contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ViewsEnum.BOOK_STATS.name)
                        }
                        .size(50.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
                Text(
                    text = loginViewModel.getUser()?.displayName.toString(),
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.labelLarge,
                    color = Purple40,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                HorizontalDivider()
            }
        }
        ReadingBooks(
            books = listOf(Book("1", "Book", "asdasd da slva")),
            navController = navController
        )
    }
}

@Composable
private fun TitleSection(labelResId: Int, modifier: Modifier = Modifier) {
    Text(
        text = stringResource(labelResId),
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier
    )
}

@Composable
private fun ReadingBooks(books: List<Book>, navController: NavController) {
    Row {
        BookCardItem(book = books.first())
    }
}

@Composable
private fun BookCardItem(book: Book, onClick: (String) -> Unit = {}) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(end = 16.dp)
            .height(240.dp)
            .width(200.dp)
            .clickable { onClick(book.id) },
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Row(horizontalArrangement = Arrangement.Center) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data("imageURL")
                        .crossfade(true)
                        .transformations(RoundedCornersTransformation())
                        .build()
                )
                Image(
                    painter = painter,
                    contentDescription = "",
                    modifier = Modifier
                        .weight(.7f)
                        .height(140.dp)
                )
                Column(
                    modifier = Modifier
                        .weight(.3f)
                        .padding(top = 25.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = stringResource(
                            R.string.content_favorite_description
                        )
                    )
                    BookRating(score = 3)
                }
            }
            Text(
                text = book.title,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = book.authors,
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.labelLarge
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                RoundedButton(labelResId = R.string.reading_btn)
            }
        }
    }
}

@Composable
private fun RoundedButton(labelResId: Int, radius: Int = 30, onClick: (String) -> Unit = {}) {
    Surface(
        modifier = Modifier.clip(
            RoundedCornerShape(bottomEndPercent = radius, topStartPercent = radius)
        ),
        color = Pink40
    ) {
        Column(
            modifier = Modifier
                .width(90.dp)
                .heightIn(40.dp)
                .clickable { onClick("a") },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(labelResId),
                style = MaterialTheme.typography.labelLarge.copy(color = Color.White)
            )
        }
    }
}

@Composable
private fun BookRating(score: Int) {
    Surface(
        modifier = Modifier
            .height(70.dp)
            .padding(4.dp),
        shape = RoundedCornerShape(56.dp),
        shadowElevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = Icons.Filled.Star, contentDescription = "")
            Text(text = score.toString(), style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun FabContent(onTap: () -> Unit) {
    FloatingActionButton(
        onClick = { onTap() },
        shape = RoundedCornerShape(50.dp),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(R.string.content_fab_description),
            tint = Purple80
        )
    }
}