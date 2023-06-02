package com.plzgpt.lenzhub.ui.screen.lenz

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.net.Uri
import android.opengl.GLSurfaceView
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.view.View
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.navercorp.nid.NaverIdLoginSDK.applicationContext
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.api.dto.LenzBasicInfoDto
import com.plzgpt.lenzhub.opengl.PhotoFilter
import com.plzgpt.lenzhub.ui.theme.LHBackground
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHPoint
import com.plzgpt.lenzhub.ui.view.LongButton
import com.plzgpt.lenzhub.util.bounceClick
import java.io.*
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
    setLenz: (LenzBasicInfoDto) -> Unit = {},
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
                    ))}

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
                    val modifiedPhoto = PhotoFilter.getInstance(context, photo).getModifiedPhoto()
                    setLenz(PhotoFilter.getInstance(context, photo).getAllValue())
                    onNext(modifiedPhoto)
                },
                modifier = Modifier
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(18.dp))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
fun saveImageOnAboveAndroidQ(contentResolver: ContentResolver, bitmap: Bitmap) {
    val fileName = System.currentTimeMillis().toString() + ".png" // 파일이름 현재시간.png

    /*
    * ContentValues() 객체 생성.
    * ContentValues는 ContentResolver가 처리할 수 있는 값을 저장해둘 목적으로 사용된다.
    * */
    val contentValues = ContentValues()
    contentValues.apply {
        put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/ImageSave") // 경로 설정
        put(MediaStore.Images.Media.DISPLAY_NAME, fileName) // 파일이름을 put해준다.
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.IS_PENDING, 1) // 현재 is_pending 상태임을 만들어준다.
        // 다른 곳에서 이 데이터를 요구하면 무시하라는 의미로, 해당 저장소를 독점할 수 있다.
    }

    // 이미지를 저장할 uri를 미리 설정해놓는다.
    val uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    try {
        if(uri != null) {
            val image = contentResolver.openFileDescriptor(uri, "w", null)
            // write 모드로 file을 open한다.

            if(image != null) {
                val fos = FileOutputStream(image.fileDescriptor)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                //비트맵을 FileOutputStream를 통해 compress한다.
                fos.close()

                contentValues.clear()
                contentValues.put(MediaStore.Images.Media.IS_PENDING, 0) // 저장소 독점을 해제한다.
                contentResolver.update(uri, contentValues, null, null)
            }
        }
    } catch(e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}