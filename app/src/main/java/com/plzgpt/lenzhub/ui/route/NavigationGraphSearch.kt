package com.plzgpt.lenzhub.ui.route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plzgpt.lenzhub.ui.screen.search.SearchMainScreen


enum class NAV_ROUTE_SEARCH(val routeName: String, val description: String) { //upload 패키지 루트.
    DISCOVERMAIN("DISCVOER_SEARCH_MAIN", "탐색창 메인 창"),
    DISCOVERSEARCHING("DISCOVER_SEARCHING", "탐색 검색 창"),
    DISCOVERSEARCHRESULT("DISCOVER_SEARCH_RESULT", "탐색 결과 창"),
    DISCOVERSEARCHUSERPROFILE("DISCOVER_SEARCH_USER_PROFILE", "탐색 유저 프로필")
}


@Composable
fun NavigationGraphSearch(
    navController: NavHostController
) {

    NavHost(navController, startDestination = NAV_ROUTE_SEARCH.DISCOVERMAIN.routeName,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(
            NAV_ROUTE_SEARCH.DISCOVERMAIN.routeName
        ) {
            SearchMainScreen(navController)
        }
        composable(
            NAV_ROUTE_SEARCH.DISCOVERSEARCHING.routeName,
        ) { backStackEntry ->

        }
        composable(
            NAV_ROUTE_SEARCH.DISCOVERSEARCHRESULT.routeName + "/{searchText}",
        ) { backStackEntry ->

        }
        composable(
            NAV_ROUTE_SEARCH.DISCOVERSEARCHUSERPROFILE.routeName
        ) {

        }
    }
}