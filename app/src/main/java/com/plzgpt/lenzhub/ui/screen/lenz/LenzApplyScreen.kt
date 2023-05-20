package com.plzgpt.lenzhub.ui.screen.lenz

import android.app.Activity
import android.graphics.BitmapFactory
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
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.ApplyViewModel
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.util.bounceClick

enum class LenzApplyScreen (val title: String) {
    Apply(title = "렌즈 적용"),
    Result(title = "적용 결과"),
    Picture(title = "사진 선택")
}
@Composable
fun LenzApplyScreen(
    context: Activity,
    viewModel: ApplyViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LenzApplyScreen.valueOf(
        backStackEntry?.destination?.route ?: LenzApplyScreen.Picture.name
    )
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
                if(currentScreen == LenzApplyScreen.Picture) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_photo_library),
                        contentDescription = null,
                        modifier = Modifier
                            .bounceClick {
                                navController.navigate(route = LenzApplyScreen.Picture.name)
                            }
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                }
            }
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = LenzApplyScreen.Picture.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LenzApplyScreen.Picture.name) {
                LenzPicture(
                    defaultImage = uiState.photo,
                    onTakePhoto = {
                        viewModel.setPicture(it)
                        viewModel.setPictureBitmap(
                            BitmapFactory.decodeFileDescriptor(
                            context.contentResolver.openFileDescriptor(it, "r")?.fileDescriptor, null, null
                        ))
                    },
                    onNext = { navController.navigate(route = LenzApplyScreen.Apply.name) }
                )
            }
            composable(route = LenzApplyScreen.Apply.name) {
                LenzApplier(
                    photo = uiState.photoBitmap,
                    onNext = {
                        navController.navigate(route = LenzApplyScreen.Result.name)
                        viewModel.setModifiedPicture(it)
                    }
                )
            }
            composable(route = LenzApplyScreen.Result.name) {
                LenzApplyResult(
                    onNext = { context.finish() },
                    uiState.modifiedPhotoBitmap
                )
            }
        }
    }
}