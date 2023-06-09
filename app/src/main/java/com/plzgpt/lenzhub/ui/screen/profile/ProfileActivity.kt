package com.plzgpt.lenzhub.ui.screen.profile

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.plzgpt.lenzhub.ui.screen.lenz.post.LenzPostScreen

class ProfileActivity  : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Profile2Screen(intent.getIntExtra("profileId", 0))
            Log.d("ProfileActivity", "profileId: ${intent.getIntExtra("profileId", 0)}")
            }
        }
    }
}