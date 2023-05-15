package com.plzgpt.lenzhub.ui.screen.upload

import android.graphics.Bitmap
import android.net.Uri

data class UploadUiState(
    val photo: Uri = Uri.EMPTY,
    val photoBitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
)