package com.plzgpt.lenzhub.ui.screen.lenz

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.GLSurfaceView
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.opengl.PhotoFilter
import com.plzgpt.lenzhub.ui.theme.LHBackground
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHPoint
import com.plzgpt.lenzhub.ui.view.LongButton
import com.plzgpt.lenzhub.util.bounceClick
import java.io.OutputStream
import kotlin.math.roundToInt


enum class FilterType (val ko: String) {
    Brightness("밝기"),
    Contrast("대비"),
    Backlight("역광"),
    Saturate("색조"),
    Grain("그레인"),
    Temperature("따뜻함"),
    Sharpen("선명도"),
    Distortion("왜곡")
}

data class Filter (
    var type: FilterType,
    var value: Float,
    var icon: Int,
    var isSelected: Boolean = false
)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun LenzMaker(
    photo: Bitmap = BitmapFactory.decodeResource(LocalContext.current.resources, R.drawable.ic_lenz_apply),
    onNext: (Bitmap) -> Unit = {}
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
            var currentValue by remember { mutableStateOf(1f) }
            var currentFilter by remember { mutableStateOf(FilterType.Brightness) }
            var currentIndex by remember { mutableStateOf(0) }
            val filters by remember { mutableStateOf(
                    listOf(
                        Filter(FilterType.Brightness, 1f, R.drawable.ic_effect_brightness, true),
                        Filter(FilterType.Contrast, 1f, R.drawable.ic_effect_contrast),
                        Filter(FilterType.Backlight, 0f, R.drawable.ic_effect_backlight),
                        Filter(FilterType.Saturate, 0f, R.drawable.ic_effect_color),
                        Filter(FilterType.Sharpen, 0f, R.drawable.ic_effect_sharpen),
                        Filter(FilterType.Grain, 0f, R.drawable.ic_effect_grain),
                        Filter(FilterType.Distortion, 0f, R.drawable.ic_effect_distort),
                        Filter(FilterType.Temperature, 0.5f, R.drawable.ic_effect_temper)
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
                        currentValue -= 0.02f
                        PhotoFilter.getInstance(context, photo).setFilterValue(currentFilter.name, currentValue)
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
                        currentValue += 0.02f
                        PhotoFilter.getInstance(context, photo).setFilterValue(currentFilter.name, currentValue)
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
                    .wrapContentHeight(),
                contentPadding = PaddingValues(horizontal = 18.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(items = filters, key = { it.type }) {
                    Column(
                        modifier = Modifier
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
                                    color = if (it.type == currentFilter) LHPoint else LHBackground,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                painter = painterResource(id = it.icon),
                                contentDescription = null,
                                modifier = Modifier
                                    .wrapContentSize(),
                                tint = if (it.type == currentFilter) LHPoint else LHBackground
                            )
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
                    onNext(PhotoFilter.getInstance(context, photo).getModifiedPhoto())
                    saveImageToGallery(photo, context.contentResolver, System.currentTimeMillis().toString() + ".jpeg")
                },
                modifier = Modifier
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun saveImageToGallery(bitmap: Bitmap, contentResolver: ContentResolver, displayName: String) {
    val imageCollection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    val imageDetails = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    }

    var imageUri: Uri? = null
    try {
        imageUri = contentResolver.insert(imageCollection, imageDetails)
        imageUri?.let {
            val outputStream: OutputStream? = contentResolver.openOutputStream(it)
            outputStream?.use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
                    Toast.makeText(applicationContext, "Failed to save image", Toast.LENGTH_SHORT).show()
                }
            }
        }
    } catch (e: Exception) {
        Toast.makeText(applicationContext, "Failed to save image: ${e.message}", Toast.LENGTH_SHORT).show()
    } finally {
        imageUri?.let {
            contentResolver.delete(it, null, null)
        }
    }
}