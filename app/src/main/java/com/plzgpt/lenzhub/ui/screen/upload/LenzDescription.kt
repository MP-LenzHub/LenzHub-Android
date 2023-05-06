package com.plzgpt.lenzhub.ui.screen.upload

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plzgpt.lenzhub.ui.theme.mainBlack
import com.plzgpt.lenzhub.ui.view.LongButton
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.theme.mainPoint
import com.plzgpt.lenzhub.ui.view.EditText
import com.plzgpt.lenzhub.util.addFocusCleaner
import com.plzgpt.lenzhub.util.bounceClick

@Composable
fun LenzDescription() {
    var focusManager = LocalFocusManager.current
    var title = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
    ) {
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Text(
            text = "제목",
            modifier = Modifier
                .padding(start = 18.dp),
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        )
        Spacer(modifier = Modifier.padding(top = 18.dp))
        EditText(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth(),
            title = "",
            data = title,
            isTextFieldFocused = remember { mutableStateOf(false) }
        )
        Spacer(modifier = Modifier.padding(top = 20.dp))
        Text(
            text = "카테고리",
            modifier = Modifier
                .padding(start = 18.dp),
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        )
        Spacer(modifier = Modifier.padding(top = 18.dp))

        var checkState = listOf(
            remember { mutableStateOf(false) },
            remember { mutableStateOf(false) },
            remember { mutableStateOf(false) },
            remember { mutableStateOf(false) }
        )
        val category = listOf("풍경", "인물", "음식", "동물")
        for(i in 0..3) {
            Category(
                name = category[i],
                isSelected = checkState[i]
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
        Spacer(modifier = Modifier.height(18.dp))
        Spacer(modifier = Modifier.weight(1f))
        LongButton(
            text = "업로드",
            onClick = {
                // 렌즈 업로드 api
                // 성공 시 결과 화면 후 메인
                // 실패 시 메인 및 실패 메세지
            }
        )
        Spacer(modifier = Modifier.height(18.dp))
    }
}

@Composable
fun Category(
    name: String,
    isSelected: MutableState<Boolean> = mutableStateOf(false),
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
    ) {
        Text(
            text = name,
            color = mainBlack,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter =
            if(isSelected.value) {
                painterResource(id = R.drawable.ic_check_on)
            } else {
                painterResource(id = R.drawable.ic_check_off)
            },
            tint =
            if(isSelected.value) {
                mainPoint
            } else {
                mainBlack
            },
            contentDescription = null,
            modifier = Modifier
                .bounceClick { isSelected.value = !isSelected.value }
        )
    }
}