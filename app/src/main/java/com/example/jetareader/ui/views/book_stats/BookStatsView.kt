package com.example.jetareader.ui.views.book_stats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetareader.R
import com.example.jetareader.ui.views.home.HomeViewModel
import com.example.jetareader.ui.widgets.AppBarWidget

@Composable
fun BookStatsView(navController: NavHostController) {
    val homeViewModel: HomeViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            AppBarWidget(
                titleResId = R.string.book_stats_title,
                onBackAction = { navController.popBackStack() },
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                shape = CircleShape,
                elevation = CardDefaults.cardElevation(5.dp),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        modifier = Modifier.padding(start = 25.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Icon(imageVector = Icons.Sharp.Person, contentDescription = "")
                        Text(
                            text = homeViewModel.getUser()?.displayName.toString(),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                    Column(modifier = Modifier.padding(start = 25.dp, top = 4.dp, bottom = 4.dp)) {
                        Text(
                            text = stringResource(R.string.your_stats_title),
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        HorizontalDivider()
                        Text(
                            text = stringResource(
                                R.string.you_re_reading_s_books,
                                homeViewModel.getReadingBooks().size,
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                        Text(
                            text = stringResource(
                                R.string.you_ve_read_s_books,
                                homeViewModel.getReadingBooks().size,
                            ),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                }
            }
        }
    }
}