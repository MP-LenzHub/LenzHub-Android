package com.plzgpt.lenzhub.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plzgpt.lenzhub.ui.theme.mainPoint
import com.plzgpt.lenzhub.util.bounceClick

@Composable
@Preview(showBackground = false)
fun LongButton(
    modifier: Modifier = Modifier,
    text: String = "디폴트",
    textColor: Color = Color.White,
    textSize: Float = 16f,
    buttonColor: Color = mainPoint,
    onClick: () -> Unit = { }
) {
    Card (
        modifier = modifier
            .bounceClick { onClick() }
            .fillMaxWidth()
            .height(50.dp)
            .padding(horizontal = 18.dp),
        shape = RoundedCornerShape(15.dp),
        backgroundColor = buttonColor,
        elevation = 0.dp
    ) {
        Text (
            text = text,
            color = textColor,
            fontSize = textSize.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .wrapContentSize()
        )
    }
}