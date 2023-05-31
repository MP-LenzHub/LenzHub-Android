package com.plzgpt.lenzhub.ui.screen.lenz

import android.graphics.Bitmap
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.view.LongButton
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.api.dto.LenzBasicInfoDto
import com.plzgpt.lenzhub.api.dto.PostCreateReqDto
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.UploadViewModel
import com.plzgpt.lenzhub.ui.theme.LHPoint
import com.plzgpt.lenzhub.ui.view.EditText
import com.plzgpt.lenzhub.util.UriUtil
import com.plzgpt.lenzhub.util.addFocusCleaner
import com.plzgpt.lenzhub.util.bounceClick
import com.skydoves.landscapist.glide.GlideImage
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@Composable
fun LenzDescription(
    originPhoto: Bitmap,
    modifiedPhoto: Bitmap,
    lenzInfo: LenzBasicInfoDto,
    onNext: (RequestBody) -> Unit
) {
    var focusManager = LocalFocusManager.current
    var title = remember { mutableStateOf("") }
    var description = remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
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
        Spacer(modifier = Modifier.height(10.dp))
        EditText(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth(),
            title = "",
            data = title,
            isTextFieldFocused = remember { mutableStateOf(false) }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "렌즈 설명",
            modifier = Modifier
                .padding(start = 18.dp),
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        EditText(
            modifier = Modifier
                .padding(horizontal = 18.dp)
                .fillMaxWidth(),
            title = "",
            data = description,
            isTextFieldFocused = remember { mutableStateOf(false) }
        )
        Spacer(modifier = Modifier.height(10.dp))
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
        ) {
            GlideImage(
                imageModel = originPhoto,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
            Spacer(modifier = Modifier.width(18.dp))
            GlideImage(
                imageModel = modifiedPhoto,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        LongButton(
            text = "업로드",
            onClick = {
                // 렌즈 업로드 api
                // 성공 시 결과 화면 후 메인
                // 실패 시 메인 및 실패 메세지
                val post = PostCreateReqDto(
                    title = title.value,
                    price = 0,
                    description = description.value,
                    category_name = category[checkState.indexOfFirst { it.value }],
                    lenzBasicInfoDto = lenzInfo
                ).toString().toRequestBody("application/json; charset=utf-8".toMediaType())

                val jsonLenz = JsonObject().apply {
                    addProperty("brightness", lenzInfo.brightness)
                    addProperty("contrast", lenzInfo.contrast)
                    addProperty("saturate", lenzInfo.saturate)
                    addProperty("backLight", lenzInfo.backLight)
                    addProperty("grain", lenzInfo.grain)
                    addProperty("temperature", lenzInfo.temperature)
                    addProperty("sharpen", lenzInfo.sharpen)
                    addProperty("distortion", lenzInfo.distortion)
                }

                val jsonData = JsonObject().apply {
                    addProperty("title", title.value)
                    addProperty("price", 0)
                    addProperty("description", description.value)
                    addProperty("category_name", category[checkState.indexOfFirst { it.value }])
                    add("lenzBasicInfoDto", jsonLenz)
                }

                val jsonBody = jsonData.toString().toRequestBody("application/json; charset=utf-8".toMediaType())


                onNext(jsonBody)
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
            color = LHBlack,
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
                LHPoint
            } else {
                LHBlack
            },
            contentDescription = null,
            modifier = Modifier
                .bounceClick { isSelected.value = !isSelected.value }
        )
    }
}