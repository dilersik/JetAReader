package com.example.jetareader.ui.widgets

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.jetareader.R
import com.example.jetareader.model.Book
import com.example.jetareader.model.MBook
import com.example.jetareader.ui.navigation.ViewsEnum

@Composable
fun BookRow(
    context: Context,
    navController: NavController,
    book: Book? = null,
    mBook: MBook? = null
) {
    BoxWithConstraints {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxHeight)
                .padding(bottom = 16.dp)
                .clickable {
                    navController.navigate(ViewsEnum.BOOK_DETAILS.name + "/${book?.id ?: mBook?.googleBookId}")
                },
            shape = RectangleShape,
            elevation = CardDefaults.cardElevation(7.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        ) {
            Row(modifier = Modifier.padding(8.dp)) {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(book?.volumeInfo?.imageLinks?.thumbnail ?: mBook?.photoUrl)
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
                        text = book?.volumeInfo?.title ?: mBook?.title.toString(),
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 2,
                    )
                    Text(
                        text = book?.volumeInfo?.subtitle ?: mBook?.description.toString(),
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 2,
                    )
                    Text(
                        text = stringResource(
                            R.string.book_date_lbl,
                            book?.volumeInfo?.publishedDate ?: mBook?.publishedDate.toString(),
                        ),
                        modifier = Modifier.padding(top = 6.dp),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Text(
                        text = stringResource(
                            R.string.book_author_lbl,
                            book?.volumeInfo?.authors ?: mBook?.authors.toString(),
                        ),
                        overflow = TextOverflow.Clip,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}
