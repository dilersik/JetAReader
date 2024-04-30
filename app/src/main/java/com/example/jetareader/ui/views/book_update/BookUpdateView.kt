package com.example.jetareader.ui.views.book_update

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.jetareader.R
import com.example.jetareader.model.MBook
import com.example.jetareader.ui.navigation.ViewsEnum
import com.example.jetareader.ui.views.book_update.BookUpdateViewConstants.PARAM
import com.example.jetareader.ui.widgets.AppBarWidget
import com.example.jetareader.ui.widgets.InputField
import com.example.jetareader.ui.widgets.RatingBarWidget
import com.example.jetareader.ui.widgets.ShowAlertDialogWidget
import com.example.jetareader.utils.formatDate
import com.example.jetareader.utils.showToast

@Composable
fun BookUpdateView(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry,
) {
    val bookId = navBackStackEntry.arguments?.getString(PARAM) ?: ""
    val viewModel: BookUpdateViewModel = hiltViewModel()
    LaunchedEffect(key1 = true) {
        viewModel.getBookById(bookId)
    }

    Scaffold(
        topBar = {
            AppBarWidget(
                titleResId = R.string.book_update_title,
                onBackAction = { navController.popBackStack() },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(ScrollState(0)),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            when {
                viewModel.loading -> LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                viewModel.error != null -> LocalContext.current.showToast(viewModel.error!!)
                else -> {
                    val mBook = viewModel.book
                    val ratingState = remember { mutableIntStateOf(mBook.rating ?: 0) }
                    val hasChanged = remember { mutableStateOf(false) }
                    val isFinished = remember { mutableStateOf(mBook.finishedReading != null) }
                    val isStarted = remember { mutableStateOf(mBook.startedReading != null) }
                    val notesState = rememberSaveable { mutableStateOf(mBook.notes ?: "") }
                    CardBook(mBook)
                    FormBook(mBook, hasChanged, notesState)
                    ReadingLinksBook(mBook, hasChanged, isFinished, isStarted)
                    RatingBook(ratingState, mBook, hasChanged)
                    ButtonsBook(
                        mBook,
                        notesState,
                        ratingState,
                        isStarted,
                        isFinished,
                        hasChanged,
                        viewModel,
                        navController
                    )
                }
            }
        }
    }
}

@Composable
private fun RatingBook(
    ratingState: MutableIntState,
    mBook: MBook,
    hasChanged: MutableState<Boolean>
) {
    RatingBarWidget(
        ratingState.intValue,
        modifier = Modifier.padding(top = 40.dp)
    ) { chosenRating ->
        ratingState.intValue = chosenRating
        if (ratingState.intValue != mBook.rating) {
            hasChanged.value = true
        }
    }
}

@Composable
private fun ButtonsBook(
    mBook: MBook,
    notesState: MutableState<String>,
    ratingState: MutableState<Int>,
    isStarted: MutableState<Boolean>,
    isFinished: MutableState<Boolean>,
    hasChanged: MutableState<Boolean>,
    viewModel: BookUpdateViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }
    ShowAlertDialogWidget(
        titleResId = R.string.delete_alert,
        textResId = R.string.delete_message,
        openDialog = openDialog,
        onConfirm = {
            viewModel.deleteBook(mBook) {
                navController.navigate(ViewsEnum.HOME.name)
            }
        }
    )

    Row(
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        ActionButton(Icons.Default.Edit, hasChanged.value, R.string.save_btn) {
            mBook.rating = ratingState.value
            mBook.notes = notesState.value
            viewModel.updateBook(mBook, isFinished.value, isStarted.value) {
                context.showToast(context.getString(R.string.book_updated_message))
                navController.navigate(ViewsEnum.HOME.name)
            }
        }
        ActionButton(Icons.Default.Delete, true, R.string.delete_btn) {
            openDialog.value = true
        }
    }
}

@Composable
private fun ActionButton(icon: ImageVector, enabled: Boolean, textResId: Int, onClick: () -> Unit) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier.width(120.dp),
        enabled = enabled,
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = Color.Gray
        ),
    ) {
        Row {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.LightGray,
            )
            Text(stringResource(textResId), color = Color.White)
        }
    }
}

@Composable
private fun ReadingLinksBook(
    mBook: MBook,
    hasChanged: MutableState<Boolean>,
    isFinished: MutableState<Boolean>,
    isStarted: MutableState<Boolean>
) {
    Row {
        TextButton(
            onClick = {
                isStarted.value = !isStarted.value
                hasChanged.value = true
            },
            enabled = !isStarted.value
        ) {
            if (isStarted.value) {
                Text(
                    text = stringResource(
                        R.string.started_reading_txt,
                        mBook.startedReading?.formatDate().toString(),
                    ),
                    color = Color.LightGray,
                )
            } else {
                Text(text = stringResource(R.string.start_reading_now_link))
            }
        }

        TextButton(onClick = {
            isFinished.value = !isFinished.value
            hasChanged.value = true
        }, enabled = !isFinished.value) {
            if (isFinished.value) {
                Text(
                    text = stringResource(
                        R.string.finished_reading_txt,
                        mBook.finishedReading?.formatDate().toString(),
                    ),
                    color = Color.LightGray,
                )
            } else {
                Text(text = stringResource(R.string.finish_reading_now_link))
            }
        }
    }
}

@Composable
private fun FormBook(
    mBook: MBook,
    hasChanged: MutableState<Boolean>,
    notesState: MutableState<String>
) {
    val notes = mBook.notes ?: ""
    if (notesState.value != notes) {
        hasChanged.value = true
    }
    InputField(
        modifier = Modifier
            .height(140.dp)
            .padding(20.dp),
        state = notesState,
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
            .padding(start = 4.dp, end = 4.dp, bottom = 8.dp)
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
                    maxLines = 1,
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
