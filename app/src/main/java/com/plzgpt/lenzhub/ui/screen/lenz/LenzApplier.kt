package com.plzgpt.lenzhub.ui.screen.lenz

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLSurfaceView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.opengl.PhotoFilter
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.Lenz
import com.plzgpt.lenzhub.ui.theme.LHBackground
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHGray
import com.plzgpt.lenzhub.ui.theme.LHPoint
import com.plzgpt.lenzhub.ui.view.LongButton
import com.plzgpt.lenzhub.util.bounceClick

@Composable
fun LenzApplier(
    photo: Bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.ic_lenz_apply),
    onNext: (Bitmap) -> Unit = {},
    lenzList: List<Lenz> = listOf(Lenz(name = "렌즈1"), Lenz(name = "렌즈2"), Lenz(name = "렌즈3"))
) {
    Column(
        // width == height
        modifier = Modifier
            .fillMaxSize()
            .background(LHBackground)
    ) {
        val context = LocalContext.current

        AndroidView(
            factory = {
                GLSurfaceView(it).apply {
                    setEGLContextClientVersion(2)
                    setRenderer(PhotoFilter.getInstance(it, photo))
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
            val currentLenz = remember { mutableStateOf(lenzList[0]) }

            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .weight(1f)
            ) {
                items(items = lenzList, key = { it.name }) { lenz ->
                    LenzListItem(lenz, currentLenz)
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
            LongButton(
                text = "다음",
                onClick = {
                    onNext(PhotoFilter.getInstance(context, photo).getModifiedPhoto())
                },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@Preview
@Composable
fun LenzListItem(
    lenz: Lenz = Lenz(),
    currentLenz: MutableState<Lenz> = mutableStateOf(Lenz())
) {
    Card(
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(20.dp))
            .bounceClick {
                currentLenz.value = lenz
            }
            .border(
                width = 1.dp,
                color = if (lenz.name == currentLenz.value.name) LHPoint else LHBackground,
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = lenz.name,
                style = TextStyle(
                    color = LHBlack,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .wrapContentSize()
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = lenz.categories.joinToString(", "),
                style = TextStyle(
                    color = LHGray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .wrapContentSize()
            )
            Spacer(modifier = Modifier.width(20.dp))
        }
    }
}