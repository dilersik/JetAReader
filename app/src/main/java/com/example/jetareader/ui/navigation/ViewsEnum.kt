package com.example.jetareader.ui.navigation

enum class ViewsEnum {
    SPLASH,
    LOGIN,
    CREATE_ACCOUNT,
    HOME,
    BOOK_SEARCH,
    BOOK_DETAILS,
    BOOK_UPDATE,
    BOOK_STATS;

    companion object {
        fun fromRout(route: String): ViewsEnum = when (route.substringBefore("/")) {
            SPLASH.name -> SPLASH
            LOGIN.name -> LOGIN
            CREATE_ACCOUNT.name -> CREATE_ACCOUNT
            HOME.name -> HOME
            BOOK_SEARCH.name -> BOOK_SEARCH
            BOOK_DETAILS.name -> BOOK_DETAILS
            BOOK_UPDATE.name -> BOOK_UPDATE
            BOOK_STATS.name -> BOOK_STATS
            else -> throw IllegalArgumentException("ViewsEnum.fromRout: invalid route $route")
        }
    }
}