package com.plzgpt.lenzhub.ui.screen.lenz.post

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

class LenzPostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Log.d("LenzPostActivity", "profileId??: ${intent.getIntExtra("postId", 0)}")
                LenzPostScreen(this, intent.getIntExtra("postId", 0))
            }
        }
    }
}