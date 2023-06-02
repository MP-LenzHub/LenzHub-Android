package com.plzgpt.lenzhub.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*

import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.plzgpt.lenzhub.ApplicationClass
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.screen.lenz.post.LenzPostScreen
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.UserViewModel
import com.plzgpt.lenzhub.ui.screen.main.ProfileInfo
import com.plzgpt.lenzhub.ui.screen.search.SearchCategoryLikeScreen
import com.plzgpt.lenzhub.util.PostHeartCard
import com.plzgpt.lenzhub.util.addFocusCleaner
import com.plzgpt.lenzhub.util.bounceClick
import kotlinx.coroutines.launch


@Composable
fun Profile2Screen(
    userIdx : Int,
    navController: NavHostController = rememberNavController()
) {
    val mContext = LocalContext.current
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = LenzPostScreen.valueOf(
        backStackEntry?.destination?.route ?: LenzPostScreen.Post.name
    )

    val isUserInfo = remember { mutableStateOf(false) }
    var viewModel : UserViewModel = UserViewModel()


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 40.dp)
    ) {
        MainScreen(userIdx, viewModel)
    }
}
