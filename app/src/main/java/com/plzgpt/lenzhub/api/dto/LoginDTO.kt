package com.plzgpt.lenzhub.api.dto

data class SignInResponseDTO(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: SignInResult
)

data class SignInResult(
    val success: Boolean
)

data class LogInResponseDTO(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: SignInResult
)

data class LogInResult(
    val success: Boolean
)