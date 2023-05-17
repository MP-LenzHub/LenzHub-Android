package com.plzgpt.lenzhub.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.plzgpt.lenzhub.ui.screen.signin.SignInScreen
import com.plzgpt.lenzhub.ui.theme.LenzhubTheme

class SignInActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LenzhubTheme {
                SignInScreen()
            }
        }
    }
}