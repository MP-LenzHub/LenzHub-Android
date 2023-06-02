package com.plzgpt.lenzhub.ui.screen.lenz.post

import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.plzgpt.lenzhub.ApplicationClass.Companion.clientId
import com.plzgpt.lenzhub.ApplicationClass.Companion.sharedPreferences
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.PostViewModel
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.util.bounceClick
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

enum class LenzPostScreen (val title: String) {
    Post(title = "렌즈 상세"),
    Pay(title = "렌즈 구매"),
    Save(title = "렌즈 저장")
}

@Composable
fun LenzPostScreen(
    context: Activity,
    postId: Int = 0,
    viewModel: PostViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LenzPostScreen.valueOf(
        backStackEntry?.destination?.route ?: LenzPostScreen.Post.name
    )
    val userId = sharedPreferences.getInt(clientId, 0)

    Scaffold (
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp
            ) {
                Spacer(modifier = Modifier.width(18.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null,
                    modifier = Modifier
                        .bounceClick {
                            if (navController.previousBackStackEntry != null) {
                                navController.navigateUp()
                            } else {
                                context.finish()
                            }
                        }
                )
                Spacer(modifier = Modifier.width(18.dp))
                Text(
                    text = currentScreen.title,
                    color = LHBlack,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    ) { innerPadding ->
        val uiStateFlow = viewModel.uiState
        val uiState by viewModel.uiState.collectAsState()
        Log.d("LenzPostScreen, ProfileId", "postId: $postId")
        viewModel.getPostUiState(postId)

        Log.d("LenzPostScreen, ProfileId", "uiState: $uiState")

        if(uiState.id != 0) {
            NavHost(
                navController = navController,
                startDestination = LenzPostScreen.Post.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = LenzPostScreen.Post.name) {
                    LenzPostDetailScreen(
                        uiStateFlow = uiStateFlow,
                        onNext = {
                            if (uiState.price == 0)
                                navController.navigate(route = LenzPostScreen.Save.name)
                            else
                                navController.navigate(route = LenzPostScreen.Pay.name)
                            viewModel.savePost(userId, uiState.id)
                        }
                    )
                }
                composable(route = LenzPostScreen.Pay.name) {
                    LenzPayScreen(
                        onNext = { context.finish() }
                    )
                }
                composable(route = LenzPostScreen.Save.name) {
                    LenzSaveScreen(
                        title = uiState.title,
                        beforeImg = uiState.beforeImg,
                        afterImg = uiState.afterImg,
                        onNext = { context.finish() }
                    )
                }
            }
        }
    }
}