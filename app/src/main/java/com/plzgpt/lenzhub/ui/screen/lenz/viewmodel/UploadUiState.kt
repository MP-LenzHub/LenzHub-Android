package com.plzgpt.lenzhub.ui.screen.lenz.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import com.plzgpt.lenzhub.api.dto.LenzBasicInfoDto
import okhttp3.MultipartBody

data class UploadUiState(
    val photo: Uri = Uri.EMPTY,
    val photoBitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val modifiedPhotoBitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val lenzInfo: LenzBasicInfoDto = LenzBasicInfoDto(1f, 1f, 0f, 0f, 0f, 0f, 0f, 0.5f),
    val beforeFile: MultipartBody.Part? = null,
    val afterFile: MultipartBody.Part? = null
)
