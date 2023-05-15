package com.plzgpt.lenzhub.ui.screen.upload

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.util.bounceClick
import androidx.lifecycle.viewmodel.compose.viewModel

enum class LenzMakeScreen (val title: String) {
    Maker(title = "렌즈 제작"),
    Description(title = "렌즈 설명"),
    Result(title = "업로드 결과"),
    Picture(title = "사진 선택")
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun LenzMakeScreen (
    context: Activity,
    viewModel: UploadViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LenzMakeScreen.valueOf(
        backStackEntry?.destination?.route ?: LenzMakeScreen.Picture.name
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
            }
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        val context = LocalContext.current

        NavHost(
            navController = navController,
            startDestination = LenzMakeScreen.Picture.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LenzMakeScreen.Picture.name) {
                LenzPicture(
                    defaultImage = uiState.photo,
                    onTakePhoto = {
                        viewModel.setPicture(it)
                        viewModel.setPictureBitmap(BitmapFactory.decodeFileDescriptor(
                            context.contentResolver.openFileDescriptor(it, "r")?.fileDescriptor, null, null
                        ))
                                  },
                    onNext = { navController.navigate(route = LenzMakeScreen.Result.name) }
                )
            }
            composable(route = LenzMakeScreen.Maker.name) {
                LenzMaker(
                    photo = uiState.photoBitmap,
                    onNext = { navController.navigate(route = LenzMakeScreen.Description.name) }
                )
            }
            composable(route = LenzMakeScreen.Description.name) {
                LenzDescription(viewModel)
            }
            composable(route = LenzMakeScreen.Result.name) {
                LenzMakeResult(
                    onNext = { navController.navigate(route = LenzMakeScreen.Maker.name) }
                )
            }
        }
    }
}