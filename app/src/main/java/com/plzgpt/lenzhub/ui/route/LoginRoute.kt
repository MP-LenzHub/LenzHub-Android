package com.plzgpt.lenzhub.ui.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plzgpt.lenzhub.ui.screen.login.LoginScreen
import com.plzgpt.lenzhub.ui.screen.login.SignInScreen

enum class NAV_ROUTE_LOGIN(val routeName: String, val description: String) {
    LOGIN("LOGIN_MAIN", "로그인 창"),
    SIGNIN("SIGNIN_MAIN", "회원가입 창"),
}

@Composable
fun NavigationGraphLogin(navController: NavHostController) {
    NavHost(navController, startDestination = NAV_ROUTE_LOGIN.LOGIN.routeName) {
        composable(NAV_ROUTE_LOGIN.LOGIN.routeName) { LoginScreen() }

        composable(NAV_ROUTE_LOGIN.SIGNIN.routeName) { SignInScreen() }
    }
}