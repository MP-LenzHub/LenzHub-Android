package com.plzgpt.lenzhub.ui.screen.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.plzgpt.lenzhub.ui.data.RecentSearch
import com.plzgpt.lenzhub.ui.data.RecentSearchDatabase

@Composable
fun SearchBefore(navController: NavHostController, db: RecentSearchDatabase, recentSearchItems : List<RecentSearch>) {

    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 18.dp, vertical = 18.dp)
    ) {
        itemsIndexed(recentSearchItems.reversed()) { idx, item ->
            SearchBeforeCard(navController , recentSearch = item, db = db)
        }
    }
}