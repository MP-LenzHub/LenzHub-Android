package com.plzgpt.lenzhub.ui.screen.login

import android.content.Intent
import android.os.Handler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
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
import androidx.core.content.ContextCompat.startActivity
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.activity.LoginActivity
import com.plzgpt.lenzhub.ui.activity.MainActivity
import com.plzgpt.lenzhub.ui.activity.SignInActivity
import com.plzgpt.lenzhub.ui.route.NAV_ROUTE_SEARCH
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHErrorAlpha
import com.plzgpt.lenzhub.ui.theme.LHPoint
import com.plzgpt.lenzhub.util.EditText
import com.plzgpt.lenzhub.util.addFocusCleaner
import com.plzgpt.lenzhub.util.bounceClick
import kotlinx.coroutines.delay

@Composable
fun SignInScreen() {

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

            EditText(title = "비밀번호 확인", data = textFieldCheckPw, isTextFieldFocused = isTextFieldFocusedCheckPw)

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

            EditText(title = "닉네임", data = textFieldName, isTextFieldFocused = isTextFieldFocusedName)


            Spacer(modifier = Modifier.height(50.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick {
                        isSignIn.value = true
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
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_signin_result),
            contentDescription = null,
            contentScale = ContentScale.FillWidth

        )

        val intent = Intent(mContext, LoginActivity::class.java)
            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        Handler().postDelayed({
            mContext.startActivity(intent)}, 1000L)
    }
}


