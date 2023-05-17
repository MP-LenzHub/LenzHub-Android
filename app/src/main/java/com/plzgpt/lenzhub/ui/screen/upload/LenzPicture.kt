package com.plzgpt.lenzhub.ui.screen.upload

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.Glide
import com.plzgpt.lenzhub.ui.theme.LHGreen
import com.plzgpt.lenzhub.ui.view.LongButton
import com.plzgpt.lenzhub.util.bounceClick
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@Composable
fun LenzPicture(
    defaultImage: Uri = Uri.EMPTY,
    onTakePhoto: (Uri) -> Unit = {},
    onNext: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    // 사진 가져오는 런쳐
    val takePhotoFromAlbumLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let {
                    onTakePhoto(it)
                }
            } else if (result.resultCode != Activity.RESULT_CANCELED) {
                Log.d("Image Upload", "fail")
            }
        }
    val takePhotoFromAlbumIntent =
        Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
            putExtra(
                Intent.EXTRA_MIME_TYPES,
                arrayOf("image/jpeg", "image/png", "image/bmp", "image/webp")
            )
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.weight(1f))
        GlideImage(
            imageModel = defaultImage,
            modifier = Modifier
                .size(300.dp)
                .align(CenterHorizontally)
        )
        Spacer(modifier = Modifier.weight(0.5f))
        Card (
            modifier = Modifier
                .bounceClick {
                    scope.launch {
                        takePhotoFromAlbumLauncher.launch(takePhotoFromAlbumIntent)
                    }
                }
                .width(120.dp)
                .height(40.dp)
                .align(CenterHorizontally),
            shape = RoundedCornerShape(15.dp),
            backgroundColor = LHGreen,
            elevation = 0.dp
        ) {
            Text (
                text = "사진 선택",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentSize()
            )
        }
        Spacer(modifier = Modifier.weight(0.5f))
        LongButton(
            text = "다음",
            onClick = {
                onNext()
            }
        )
        Spacer(modifier = Modifier.height(18.dp))
    }
}