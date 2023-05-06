package com.plzgpt.lenzhub.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.plzgpt.lenzhub.ui.screen.login.SignInScreen
import com.plzgpt.lenzhub.ui.theme.LenzhubTheme

class SignInActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LenzhubTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()){
                        SignInScreen()

                    }
                }
            }
        }
    }
}