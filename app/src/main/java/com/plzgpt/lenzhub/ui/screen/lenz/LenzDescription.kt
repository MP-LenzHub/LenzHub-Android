package com.plzgpt.lenzhub.ui.screen.lenz

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
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
import com.plzgpt.lenzhub.ui.data.Category
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.UploadViewModel
import com.plzgpt.lenzhub.ui.theme.LHPoint
import com.plzgpt.lenzhub.ui.view.EditText
import com.plzgpt.lenzhub.util.*
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@OptIn(ExperimentalMaterialApi::class)
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
    var price = remember { mutableStateOf("0") }
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var selectedCategory by remember { mutableStateOf(Category.ANIMAL) }
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        title = "카테고리",
        bottomSheetState = bottomSheetState,
        sheetScreen = {
            ModalBottomSheetItem(
                text = "동물",
                icon = R.drawable.ic_animal,
                trailing = true,
                modifier = Modifier.bounceClick {
                    selectedCategory = Category.ANIMAL

                    scope.launch {
                        bottomSheetState.hide()
                    }
                })
            ModalBottomSheetItem(
                text = "인물",
                icon = R.drawable.ic_person,
                trailing = true,
                modifier = Modifier.bounceClick {
                    selectedCategory = Category.PERSON

                    scope.launch {
                        bottomSheetState.hide()
                    }
                })
            ModalBottomSheetItem(
                text = "풍경",
                icon = R.drawable.ic_sight,
                trailing = true,
                modifier = Modifier.bounceClick {
                    selectedCategory = Category.SIGHT

                    scope.launch {
                        bottomSheetState.hide()
                    }
                })
            ModalBottomSheetItem(
                text = "음식",
                icon = R.drawable.ic_food,
                trailing = true,
                modifier = Modifier.bounceClick {
                    selectedCategory = Category.FOOD

                    scope.launch {
                        bottomSheetState.hide()
                    }
                })
        },
        uiScreen = {
            BackHandler(enabled = bottomSheetState.isVisible) {
                scope.launch {
                    bottomSheetState.hide()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .addFocusCleaner(focusManager)
            ) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "제목",
                    modifier = Modifier
                        .padding(start = 18.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
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
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "렌즈 설명",
                    modifier = Modifier
                        .padding(start = 18.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
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
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = "가격(원)",
                    modifier = Modifier
                        .padding(start = 18.dp),
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                EditText(
                    modifier = Modifier
                        .padding(horizontal = 18.dp)
                        .fillMaxWidth(),
                    title = "",
                    data = price,
                    isTextFieldFocused = remember { mutableStateOf(false) }
                )
                Spacer(modifier = Modifier.height(18.dp))
                Row(
                    modifier = Modifier
                        .bounceClick {
                            scope.launch {
                                bottomSheetState.show()
                            }
                        }
                ) {
                    Text(
                        text = "카테고리",
                        modifier = Modifier
                            .padding(start = 18.dp)
                            .align(CenterVertically)
                            .wrapContentHeight(),
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Image(
                        painter = painterResource(id = selectedCategory.image),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 18.dp)
                            .size(24.dp)
                            .align(CenterVertically),
                    )
                    Text(
                        text = selectedCategory.category,
                        modifier = Modifier
                            .padding(end = 18.dp)
                            .align(CenterVertically)
                            .wrapContentHeight(),
                        style = TextStyle(
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_right),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 18.dp)
                            .size(24.dp)
                            .align(CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
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
                Spacer(modifier = Modifier.height(18.dp))
                LongButton(
                    text = "업로드",
                    onClick = {
                        // 렌즈 업로드 api
                        // 성공 시 결과 화면 후 메인
                        // 실패 시 메인 및 실패 메세지

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
                            addProperty("price", price.value.toInt())
                            addProperty("description", description.value)
                            addProperty("category_name", selectedCategory.category)
                            add("lenzBasicInfoDto", jsonLenz)
                        }

                        val jsonBody = jsonData.toString()
                            .toRequestBody("application/json; charset=utf-8".toMediaType())


                        onNext(jsonBody)
                    }
                )
                Spacer(modifier = Modifier.height(18.dp))
            }
        }
    )
}