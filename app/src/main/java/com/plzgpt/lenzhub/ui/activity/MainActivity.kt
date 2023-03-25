package com.plzgpt.lenzhub.ui.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.plzgpt.lenzhub.ui.route.NAV_ROUTE_BNB
import com.plzgpt.lenzhub.ui.route.NavigationGraphBNB
import com.plzgpt.lenzhub.ui.theme.LenzhubTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LenzhubTheme {
                MainNavScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LenzhubTheme {
        Greeting("Android")
    }
}


@Composable
fun BottomNav(
    navController: NavHostController,
    scope: CoroutineScope,
    lazyScroll: LazyListState,
    navFollowController: NavHostController,
) {
    val items = listOf<NAV_ROUTE_BNB>(
        NAV_ROUTE_BNB.FOLLOW,
        NAV_ROUTE_BNB.DISCOVER,
        NAV_ROUTE_BNB.UPLOAD,
        NAV_ROUTE_BNB.MYPROFILE
    )
    androidx.compose.material.BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color(0xFFB6BFBA)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.description,
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                    )
                },
                label = { Text(item.description, fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.LightGray,
                selected = currentRoute == item.routeName,
                alwaysShowLabel = true,
                onClick = {
                    navController.navigate(item.routeName) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true

                    }
                },
            )
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainNavScreen() {
    val navController = rememberAnimatedNavController()
    val scope = rememberCoroutineScope()
    val lazyScroll = rememberLazyListState()
    val navFollowController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = { BottomNav(navController = navController,scope,lazyScroll, navFollowController = navFollowController) }
        ) {
            Box(modifier = Modifier.padding(it)) {
                NavigationGraphBNB(navController = navController, scope = scope, lazyScroll = lazyScroll,
                    navFollowController = navFollowController)

            }
        }
    }
}