package com.plzgpt.lenzhub.ui.screen.signin

import android.app.Activity
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
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
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.api.RetrofitBuilder.signupAPI
import com.plzgpt.lenzhub.api.dto.SignInResponseDTO
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
fun SignInScreen() {

    val mContext = LocalContext.current

    val textFieldId = remember { mutableStateOf("") }
    val isTextFieldFocusedId = remember { mutableStateOf(false) }

    val textFieldPw = remember { mutableStateOf("") }
    val isTextFieldFocusedPw = remember { mutableStateOf(false) }

    val textFieldCheckPw = remember { mutableStateOf("") }
    val isTextFieldFocusedCheckPw = remember { mutableStateOf(false) }


    val textFieldName = remember { mutableStateOf("") }
    val isTextFieldFocusedName = remember { mutableStateOf(false) }

    val isSignIn = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val activity = (LocalContext.current as? Activity)

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
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "회원가입",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight(700)
            )
            Spacer(modifier = Modifier.height(36.dp))
            EditText(title = "아이디", data = textFieldId, isTextFieldFocused = isTextFieldFocusedId)
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(1f)
                    .background(
                        Color(0xFFEBEEED)
                    )
            )
            Spacer(modifier = Modifier.height(20.dp))
            EditText(title = "비밀번호", data = textFieldPw, isTextFieldFocused = isTextFieldFocusedPw)

            Spacer(modifier = Modifier.height(18.dp))

            EditText(
                title = "비밀번호 확인",
                data = textFieldCheckPw,
                isTextFieldFocused = isTextFieldFocusedCheckPw
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth(1f)
                    .background(
                        Color(0xFFEBEEED)
                    )
            )
            Spacer(modifier = Modifier.height(20.dp))

            EditText(
                title = "닉네임",
                data = textFieldName,
                isTextFieldFocused = isTextFieldFocusedName
            )


            Spacer(modifier = Modifier.height(50.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick {
                        val jsonObject = JsonObject().apply {
                            addProperty("name", textFieldName.value)
                            addProperty("userId", textFieldId.value)
                            addProperty("password", textFieldCheckPw.value)
                        }
                        val mediaType = "application/json; charset=utf-8".toMediaType()
                        val body = jsonObject.toString().toRequestBody(mediaType)
                        val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), jsonObject.toString())
                        Log.d("signin",requestBody.toString())
                        signupAPI.signIn(body)
                            .enqueue(object : Callback<SignInResponseDTO> {
                                override fun onResponse(
                                    call: Call<SignInResponseDTO>,
                                    response: Response<SignInResponseDTO>
                                ) {
                                    if (response.isSuccessful) {
                                        val res = response.body()
                                        if (res != null) {
                                            if (res.isSuccess) {
                                                isSignIn.value = true
                                                Log.d("signin","성공")

                                                Log.d("signin",res.message)
                                                Toast.makeText(mContext, res.result.success.toString(), Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                                override fun onFailure(
                                    call: Call<SignInResponseDTO>,
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
                    "회원가입",
                    fontSize = 16.sp,
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

    AnimatedVisibility(
        visible = isSignIn.value,
        modifier = Modifier
            .fillMaxSize(),
        enter = fadeIn(animationSpec = tween(600)),
        exit = fadeOut(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_signin_result),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
        // 1.2초 뒤 로그인 창으로 이동
        Handler().postDelayed({ activity?.finish() }, 1200L)
    }
}


