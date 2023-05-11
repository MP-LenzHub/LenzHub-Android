package com.plzgpt.lenzhub.ui.screen.upload

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.opengl.PhotoFilter
import com.plzgpt.lenzhub.ui.theme.LHBackground
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHPoint
import com.plzgpt.lenzhub.ui.theme.LHPointAlpha
import com.plzgpt.lenzhub.ui.view.LongButton
import com.plzgpt.lenzhub.util.bounceClick
import kotlin.math.roundToInt


enum class FilterType (val ko: String) {
    Brightness("밝기"),
    Contrast("대비"),

}

data class Filter (
    var type: FilterType,
    var value: Float,
    var isSelected: Boolean = false
)
@Composable
fun LenzMaker(
    toNextScreen: () -> Unit
) {
    Column(
        // width == height
        modifier = Modifier
            .fillMaxSize()
            .background(LHBackground)
    ) {
        val context = LocalContext.current
        val photo: Bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.ic_lenz_make)
        var photoFilter = PhotoFilter(context, photo)

        AndroidView(
            factory = {
                photoFilter = PhotoFilter(it, photo)
                GLSurfaceView(it).apply {
                    setEGLContextClientVersion(2)
                    setRenderer(photoFilter)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(LHBackground)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            var currentValue by remember { mutableStateOf(1f) }
            var currentFilter by remember { mutableStateOf(FilterType.Brightness) }
            var currentIndex by remember { mutableStateOf(0) }
            val filters by remember { mutableStateOf(
                    listOf(
                        Filter(FilterType.Brightness, 1f, true),
                        Filter(FilterType.Contrast, 1f)
                    )
                ) }

//            Slider(
//                value = currentValue,
//                valueRange = 0f..2f,
//                onValueChange = {
//                    currentValue = (it * 100).roundToInt() / 100.0f
//                    photoFilter.setFilterValue(currentFilter.name, currentValue)
//                    Log.d("Slider", "changed: $it")
//                },
//                steps = 200,
//                colors = SliderDefaults.colors(
//                    thumbColor = LHPoint,
//                    activeTrackColor = LHPointAlpha,
//                    inactiveTrackColor = LHPointAlpha,
//                    activeTickColor = LHPointAlpha,
//                    inactiveTickColor = LHPointAlpha
//                ),
//                onValueChangeFinished = {
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 36.dp)
//            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp)
            ) {
                Button(
                    onClick = {
                        currentValue -= 0.01f
                        photoFilter.setFilterValue(currentFilter.name, currentValue)
                    },
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = LHBlack
                    ),
                    border = BorderStroke(1.dp, LHBlack)
                ) {
                    Text(text = "-")
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        currentValue += 0.01f
                        photoFilter.setFilterValue(currentFilter.name, currentValue)
                    },
                    elevation = ButtonDefaults.elevation(0.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = LHBlack
                    ),
                    border = BorderStroke(1.dp, LHBlack)
                ) {
                    Text(text = "+")
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            Card(
                modifier = Modifier
                    .width(60.dp)
                    .height(30.dp)
                    .align(CenterHorizontally),
                backgroundColor = Color.White,
                elevation = 0.dp,
                border = BorderStroke(1.dp, LHBlack)
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentSize(),
                    text = (currentValue * 100).roundToInt().toString()
                )
            }
            Spacer(modifier = Modifier.height(18.dp))
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                items(items = filters, key = { it.type }) {
                    Column(
                        modifier = Modifier
                            .padding(start = 18.dp)
                            .wrapContentSize(),
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .bounceClick {
                                    filters[currentIndex].value = currentValue

                                    currentIndex = filters.indexOf(it)
                                    currentValue = it.value
                                    currentFilter = it.type
                                }
                                .size(80.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = if(it.type == currentFilter) LHPoint else LHBackground,
                                    shape = CircleShape
                                )
                        ) {

                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = it.type.ko,
                            modifier = Modifier
                                .wrapContentSize()
                                .align(CenterHorizontally)
                                .padding(top = 8.dp),
                            color = if(it.type == currentFilter) LHPoint else LHBackground,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            LongButton(
                text = "다음",
                onClick = {
                    toNextScreen()
                },
                modifier = Modifier
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}