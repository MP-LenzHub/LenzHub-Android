package com.plzgpt.lenzhub.ui.view

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plzgpt.lenzhub.ui.theme.*

@Composable
fun EditText(
    modifier: Modifier,
    title: String?, //textField 위에 들어갈 제목.
    data: MutableState<String>, //textField의 데이터 값을 저장.
    isTextFieldFocused: MutableState<Boolean>, //textField가 포커싱 되어 있는지 여부.
    keyboardType: KeyboardType = KeyboardType.Text, //키보드 형식 (비밀번호, 이메일 등.)
    singleLine: Boolean = false, //textField를 한 줄 고정할 것인지 여부.
    description: String = "", //textField 아래에 들어갈 설명.
    errorListener: MutableState<Boolean> = mutableStateOf(false), //textField에 들어갈 값의 조건이 틀렸는지 여부.
    textStyle: TextStyle = TextStyle(fontSize = 20.sp, color = LHBlack), //textField의 글자 스타일 설정.
    keyboardActions: KeyboardActions = KeyboardActions(),
    placeholder: String = "",
    placeholderSize: Int = 20
) {
    val focusRequester = remember { FocusRequester() }
    Column {
        if (title!!.isNotEmpty()) {
            Text(
                title,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = if(errorListener.value) LHError else if (isTextFieldFocused.value) LHPoint else LHGray
            )
            Spacer(modifier = Modifier.height(5.dp))
        }
        TextField(
            modifier = modifier
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isTextFieldFocused.value = it.isFocused
                }
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(10.dp),
                    color = if (isTextFieldFocused.value) if (errorListener.value) LHError else LHPoint else if (errorListener.value) LHBackground else LHBackground
                )
                .animateContentSize(),
            value = data.value,
            keyboardActions = keyboardActions,
            onValueChange = { textValue ->
                data.value = textValue
            },
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = if (errorListener.value) LHError else if (isTextFieldFocused.value) LHBackground else LHBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            textStyle = textStyle,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = singleLine,
            placeholder = { Text(placeholder, color = LHGreen, fontSize = placeholderSize.sp) },
            visualTransformation =
            if (keyboardType == KeyboardType.Password) PasswordVisualTransformation()
            else VisualTransformation.None
        )
        if(description != "") {
            Spacer(modifier = Modifier.height(5.dp))
            Text(description, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = if (errorListener.value) LHError else if (isTextFieldFocused.value) LHPoint else LHGray)
        }
    }
}