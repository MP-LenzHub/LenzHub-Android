package com.plzgpt.lenzhub.ui.screen.lenz.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plzgpt.lenzhub.api.RetrofitBuilder
import com.plzgpt.lenzhub.api.dto.PostGetRes
import com.plzgpt.lenzhub.api.dto.PostGetSavedRes
import com.plzgpt.lenzhub.api.dto.PostGetSavedResResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ApplyUiState(
    val photo: Uri = Uri.EMPTY,
    val photoBitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val modifiedPhotoBitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888),
    val lenzList: ArrayList<PostUiState> = arrayListOf()
)

class ApplyViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ApplyUiState(photo = Uri.EMPTY))
    val uiState: StateFlow<ApplyUiState> = _uiState.asStateFlow()

    private val applyRepository = ApplyRepository.getInstance()

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

    fun getLenzList(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        val result = applyRepository.getLenzList(id)
        _uiState.update {currentState ->
            currentState.copy(
                lenzList = result.postList
            )
        }
    }
}

class ApplyRepository private constructor() {

    companion object {
        private var sApplyRepository: ApplyRepository = ApplyRepository()

        fun getInstance(): ApplyRepository {
            return sApplyRepository
        }
    }

    suspend fun getLenzList(id: Int): PostGetSavedResResult = withContext(Dispatchers.IO) {
        val response = RetrofitBuilder.lenzAPI.postGetSaved(id).execute()

        Log.d("ApplyRepository", "getLenzList: $response")
        Log.d("ApplyRepository", "getLenzList: ${response.body()}")

        response.body()?.result ?: PostGetSavedResResult(arrayListOf())
    }
}