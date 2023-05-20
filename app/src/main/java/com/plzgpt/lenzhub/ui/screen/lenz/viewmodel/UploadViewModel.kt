package com.plzgpt.lenzhub.ui.screen.lenz.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UploadViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UploadUiState(photo = Uri.EMPTY))
    val uiState: StateFlow<UploadUiState> = _uiState.asStateFlow()

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
}