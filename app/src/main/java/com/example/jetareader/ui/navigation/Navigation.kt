package com.example.jetareader.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetareader.ui.views.book_details.BookDetailsView
import com.example.jetareader.ui.views.book_details.BookDetailsViewConstants
import com.example.jetareader.ui.views.book_search.BookSearchView
import com.example.jetareader.ui.views.book_stats.BookStatsView
import com.example.jetareader.ui.views.book_update.BookUpdateView
import com.example.jetareader.ui.views.book_update.BookUpdateViewConstants
import com.example.jetareader.ui.views.create_account.CreateAccountView
import com.example.jetareader.ui.views.home.HomeView
import com.example.jetareader.ui.views.login.LoginView
import com.example.jetareader.ui.views.splash.SplashView

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ViewsEnum.SPLASH.name
    ) {

        composable(ViewsEnum.SPLASH.name) {
            SplashView(navController = navController)
        }

        composable(ViewsEnum.LOGIN.name) {
            LoginView(navController = navController)
        }

        composable(ViewsEnum.CREATE_ACCOUNT.name) {
            CreateAccountView(navController = navController)
        }

        composable(ViewsEnum.HOME.name) {
            HomeView(navController = navController)
        }

        composable(ViewsEnum.BOOK_SEARCH.name) {
            BookSearchView(navController = navController)
        }

        composable(
            ViewsEnum.BOOK_DETAILS.name + "/{${BookDetailsViewConstants.PARAM}}",
            arguments = listOf(navArgument(BookDetailsViewConstants.PARAM) {
                type = NavType.StringType
            })
        ) {
            BookDetailsView(navController = navController, navBackStackEntry = it)
        }

        composable(
            ViewsEnum.BOOK_UPDATE.name + "/{${BookUpdateViewConstants.PARAM}}",
            arguments = listOf(navArgument(BookUpdateViewConstants.PARAM) {
                type = NavType.StringType
            })
        ) {
            BookUpdateView(navController = navController, navBackStackEntry = it)
        }

        composable(ViewsEnum.BOOK_STATS.name) {
            BookStatsView(navController = navController)
        }
    }
}