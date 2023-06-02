package com.plzgpt.lenzhub.ui.screen.lenz.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plzgpt.lenzhub.api.RetrofitBuilder
import com.plzgpt.lenzhub.api.dto.*
import com.plzgpt.lenzhub.ui.screen.main.ProfileInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel : ViewModel() {

    private val _userState = MutableStateFlow(UserInfoResDto())
    val userState: StateFlow<UserInfoResDto> = _userState.asStateFlow()

    private val _profileState = MutableStateFlow(UserProfileInfo())
    val profileState: StateFlow<UserProfileInfo> = _profileState.asStateFlow()

    private val _followerState = MutableStateFlow(FollowListDTO())
    val followerState: StateFlow<FollowListDTO> = _followerState.asStateFlow()


    private val userRepository = UserRepository.getInstance()

    fun getUserInfo(userId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val result = userRepository.getUserInfo(userId)
        _userState.update {
            it.copy(
                id = result.id,
                name = result.name,
                userId = result.userId,
                password = result.password,
                grade = result.grade,
                profileImg = result.profileImg,
                posts = result.posts,
                likes = result.likes
            )
        }
    }

    fun getProfile(userId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val result = userRepository.getProfileInfo(userId)
        _profileState.update {
            it.copy(
                userId = result.userId,
                name = result.name,
                profileImgUrl = "",
                followCounts = result.followCounts,
                userGrade = result.userGrade,
                likedCounts = result.likedCounts,
                likedPosts = result.likedPosts,
                createPosts = result.createPosts,
            )
        }

        Log.d("결과",result.toString())
    }

    fun deleteUser(userId: Int) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.deleteProfile(userId)
    }

    fun postFollow(toUserId: Int, fromUserId: Int) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.postFollow(toUserId, fromUserId)
    }

    fun patchFollow(toUserId: Int, fromUserId: Int) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.patchFollow(toUserId, fromUserId)
    }

    fun getFollowInfo(userId: Int) = viewModelScope.launch(Dispatchers.IO) {
        val result = userRepository.getFollowInfo(userId)
        _followerState.update {
            it.copy(
                followList = result.followList
            )
        }
    }
}

class UserRepository {
    companion object {
        private var sUserRepository: UserRepository = UserRepository()

        fun getInstance(): UserRepository {
            return sUserRepository
        }
    }

    suspend fun getUserInfo(userId : Int): UserInfoResDto = withContext(Dispatchers.IO) {
        RetrofitBuilder.userAPI.getUser(userId).execute().body()?.result?: UserInfoResDto()
    }
    suspend fun getProfileInfo(userId : Int): UserProfileInfo = withContext(Dispatchers.IO) {
        RetrofitBuilder.userAPI.getProfileInfo(userId).execute().body()?.result?: UserProfileInfo()
    }
    suspend fun deleteProfile(userId : Int): Message = withContext(Dispatchers.IO) {
        RetrofitBuilder.userAPI.deleteProfile(userId).execute().body()?.result?: Message()
    }
    suspend fun postFollow(toUserId : Int, fromUserId: Int): Message = withContext(Dispatchers.IO) {
        RetrofitBuilder.userAPI.postFollow(toUserId,fromUserId ).execute().body()?.result?: Message()
    }
    suspend fun patchFollow(toUserId : Int, fromUserId: Int): Message = withContext(Dispatchers.IO) {
        RetrofitBuilder.userAPI.patchFollow(toUserId,fromUserId ).execute().body()?.result?: Message()
    }
    suspend fun getFollowInfo(userId : Int): FollowListDTO = withContext(Dispatchers.IO) {
        RetrofitBuilder.userAPI.getFollowInfo(userId).execute().body()?.result?: FollowListDTO()
    }

}