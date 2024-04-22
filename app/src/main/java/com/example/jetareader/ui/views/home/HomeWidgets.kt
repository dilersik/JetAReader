package com.example.jetareader.ui.views.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.jetareader.R
import com.example.jetareader.model.MBook
import com.example.jetareader.ui.theme.Pink40

@Composable
fun BookCardItem(mBook: MBook, navController: NavController, onClick: (String?) -> Unit = {}) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(end = 16.dp)
            .height(240.dp)
            .width(200.dp)
            .clickable { onClick(mBook.id) },
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Row(horizontalArrangement = Arrangement.Center) {
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
                text = mBook.title,
                modifier = Modifier.padding(top = 4.dp, end = 4.dp, start = 4.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = mBook.authors,
                modifier = Modifier.padding(end = 4.dp, start = 4.dp, bottom = 6.dp),
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
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
                .fillMaxHeight()
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
