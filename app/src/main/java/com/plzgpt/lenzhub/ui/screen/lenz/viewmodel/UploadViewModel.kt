package com.plzgpt.lenzhub.ui.screen.lenz.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plzgpt.lenzhub.api.dto.LenzBasicInfoDto
import com.plzgpt.lenzhub.api.dto.PostCreateReqDto
import com.plzgpt.lenzhub.util.UriUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UploadUiState(photo = Uri.EMPTY))
    val uiState: StateFlow<UploadUiState> = _uiState.asStateFlow()

    private val uploadRepository = UploadRepository.getInstance()

    fun postCreate(
        userId: Int,
        requestBody: RequestBody,
        context: Context
    ) = viewModelScope.launch(Dispatchers.IO) {
        val originalFile = UriUtil.bitmapToFile(
            uiState.value.photoBitmap, File.createTempFile("origin", ".jpg", context.cacheDir))
        val modifiedFile = UriUtil.bitmapToFile(
            uiState.value.modifiedPhotoBitmap, File.createTempFile("modified", ".jpg", context.cacheDir))

        val beforeFile = originalFile.let {
            MultipartBody.Part.createFormData(
                name = "beforeImage",
                filename = it.name,
                body = it.asRequestBody("image/*".toMediaType())
            )
        }
        val afterFile = modifiedFile.let {
            MultipartBody.Part.createFormData(
                name = "afterImage",
                filename = it.name,
                body = it.asRequestBody("image/*".toMediaType())
            )
        }

        val result = uploadRepository.postUpload(
            userId,
            requestBody,
            beforeFile,
            afterFile
        )
        Log.d("UploadViewModel", "postCreate: $result")
    }

    fun setPicture(photo: Uri) {
        _uiState.value = _uiState.value.copy(
            photo = photo
        )
    }

    fun setPictureBitmap(photoBitmap: Bitmap) {
        _uiState.value = _uiState.value.copy(
            photoBitmap = photoBitmap
        )
    }

    fun setModifiedPicture(modifiedPhotoBitmap: Bitmap) {
        _uiState.value = _uiState.value.copy(
            modifiedPhotoBitmap = modifiedPhotoBitmap
        )
    }

    fun setLenzInfo(lenzInfo: LenzBasicInfoDto) {
        _uiState.update {
            it.copy(lenzInfo = lenzInfo)
        }
    }
}