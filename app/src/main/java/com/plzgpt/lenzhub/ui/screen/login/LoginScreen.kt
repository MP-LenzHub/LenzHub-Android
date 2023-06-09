package com.plzgpt.lenzhub.ui.screen.login

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.JsonObject

import com.plzgpt.lenzhub.ApplicationClass.Companion.clientId
import com.plzgpt.lenzhub.ApplicationClass.Companion.sharedPreferences
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.api.RetrofitBuilder
import com.plzgpt.lenzhub.api.dto.LogInResponseDTO
import com.plzgpt.lenzhub.api.dto.SignInResponseDTO
import com.plzgpt.lenzhub.api.dto.User
import com.plzgpt.lenzhub.ui.activity.MainActivity
import com.plzgpt.lenzhub.ui.activity.SignInActivity
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHErrorAlpha
import com.plzgpt.lenzhub.ui.theme.LHPoint
import com.plzgpt.lenzhub.util.EditText
import com.plzgpt.lenzhub.util.addFocusCleaner
import com.plzgpt.lenzhub.util.bounceClick
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen() {

    val textFieldId = remember { mutableStateOf("") }
    val isTextFieldFocusedId = remember { mutableStateOf(false) }

    val textFieldPw = remember { mutableStateOf("") }
    val isTextFieldFocusedPw = remember { mutableStateOf(false) }

    val isLogin = remember { mutableStateOf(false) }
    val editor = sharedPreferences.edit()


    val focusManager = LocalFocusManager.current
    val mContext = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .addFocusCleaner(focusManager)
            .padding(18.dp)
    ) {

        Column(
            modifier = Modifier.padding(top = 50.dp)
        ) {
            Image(
                painter=painterResource(R.drawable.ic_icon_menu),
                contentDescription=null,
                modifier = Modifier
                    .size(width = 220.dp, height = 220.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .align(CenterHorizontally),
                contentScale = ContentScale.Crop,
            )
//            Text(text = "로그인", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = LHBlack)
            Spacer(modifier = Modifier.height(40.dp))
            EditText(title = "아이디", data = textFieldId, isTextFieldFocused = isTextFieldFocusedId)
            Spacer(modifier = Modifier.height(20.dp))
            EditText(title = "비밀번호", data = textFieldPw, isTextFieldFocused = isTextFieldFocusedPw)
            Spacer(modifier = Modifier.height(30.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick {
//                        mContext.startActivity(
//                            Intent(mContext, MainActivity::class.java)
//                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                        )
                        //테스트용

                        val loginUser = User(
                            textFieldId.value,textFieldPw.value
                        )

                        RetrofitBuilder.signupAPI.login(textFieldId.value,textFieldPw.value)
                            .enqueue(object : Callback<LogInResponseDTO> {
                                override fun onResponse(
                                    call: Call<LogInResponseDTO>,
                                    response: Response<LogInResponseDTO>
                                ) {

                                    if (response.isSuccessful) {
                                        val res = response.body()
                                        if (res != null) {
                                            if (res.isSuccess) {
                                                Toast.makeText(mContext, "환영합니다!", Toast.LENGTH_LONG).show()
                                                isLogin.value = true

                                                editor.putInt(clientId, res.result.userId)
                                                editor.apply()

                                                mContext.startActivity(
                                                    Intent(mContext, MainActivity::class.java)
                                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                                )
                                            }
                                            else{
                                               // Toast.makeText(mContext, "다시 한 번 확인해주세요.", Toast.LENGTH_LONG).show()

                                            }
                                            Toast.makeText(mContext, res.message, Toast.LENGTH_LONG).show()

                                        }
                                    }
                                }
                                override fun onFailure(
                                    call: Call<LogInResponseDTO>,
                                    t: Throwable
                                ) {
                                    Toast.makeText(mContext, "서버가 응답하지 않아요", Toast.LENGTH_SHORT).show()
                                }

                            })
                    },
                backgroundColor = LHPoint,
                shape = RoundedCornerShape(10.dp),
                elevation = 0.dp,
            ) {
                Text(
                    "로그인",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(1f)
                    .background(
                        Color(0xFFEBEEED)
                    )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick {
                        mContext.startActivity(
                            Intent(mContext, SignInActivity::class.java)
                        )
                    },
                backgroundColor = LHErrorAlpha,
                shape = RoundedCornerShape(10.dp),
                elevation = 0.dp,
            ) {
                Text(
                    "회원가입 하러가기",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }

    }
}


