package com.plzgpt.lenzhub.ui.screen.upload

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
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
import com.plzgpt.lenzhub.ui.theme.mainBlack
import com.plzgpt.lenzhub.util.bounceClick

enum class LenzMakeScreen (val title: String) {
    Maker(title = "렌즈 제작"),
    Description(title = "렌즈 설명"),
    Result(title = "업로드 결과"),
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LenzMakeScreen (
    context: Activity,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LenzMakeScreen.valueOf(
        backStackEntry?.destination?.route ?: LenzMakeScreen.Description.name
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
                    color = mainBlack,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                if (currentScreen == LenzMakeScreen.Maker) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_photo_library),
                        contentDescription = null,
                        modifier = Modifier
                            .bounceClick { }
                    )
                    Spacer(modifier = Modifier.width(18.dp))
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = LenzMakeScreen.Description.name,
            modifier = Modifier.padding(it)
        ) {
            composable(route = LenzMakeScreen.Maker.name) {
                LenzMaker(
                )
            }
            composable(route = LenzMakeScreen.Description.name) {
                LenzDescription(
                )
            }
            composable(route = LenzMakeScreen.Result.name) {
                LenzMakeResult(
                )
            }
        }
    }
}