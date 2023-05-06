package com.plzgpt.lenzhub.util

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.data.RecentSearch
import com.plzgpt.lenzhub.ui.data.RecentSearchDatabase
import com.plzgpt.lenzhub.ui.route.NAV_ROUTE_SEARCH
import com.plzgpt.lenzhub.ui.theme.LHBackground
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHGray
import com.plzgpt.lenzhub.ui.theme.LHTextFieldPoint


//탐색에 검색창
@Composable
fun SearchTextField(
    searchText : MutableState<String>,
    isTextFieldSearchFocused : MutableState<Boolean>,
    focusManager : FocusManager,
    db: RecentSearchDatabase,
    navController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(
                if (isTextFieldSearchFocused.value) LHTextFieldPoint
                else LHBackground
            )
    ) {
        //검색어 입력하는 텍스트 필드
        TextField(
            value = searchText.value,
            onValueChange = { searchText.value = it },
            modifier = Modifier
                .padding(start = 30.dp)
                .fillMaxWidth()
                .height(52.dp)
                .onFocusChanged {
                    isTextFieldSearchFocused.value = it.isFocused
                }
                .animateContentSize(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor =
                if (isTextFieldSearchFocused.value) LHTextFieldPoint
                else LHBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            textStyle = TextStyle(fontSize = 14.sp, color = LHBlack),
            keyboardActions = KeyboardActions(onSearch = {
                focusManager.clearFocus()
                isTextFieldSearchFocused.value = false

                Log.d("keyboard-test", searchText.value)
                if(searchText.value != "") {
                    //이미 전에 검색했던 거면 한번 지우고 다시 insert해줘서 맨 위로 올려줌
                    val checkData: RecentSearch? = db.recentSearchDao().findRecentSearchBySearchText(searchText.value)
                    checkData?.let {
                        db.recentSearchDao().delete(
                            it
                        )
                    }

                    db.recentSearchDao().insert(RecentSearch(searchText.value))
                    navController.navigate(route = NAV_ROUTE_SEARCH.DISCOVERSEARCHRESULT.routeName + "/" + searchText.value) {
                        popUpTo(NAV_ROUTE_SEARCH.DISCOVERSEARCHING.routeName)
                    }


                }
            }),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text
            ),
            singleLine = true,
            placeholder = {
                Text(
                    text = "제목으로 게시물을 검색해보세요",
                    style = TextStyle(color = LHGray)
                )
            }
        )
        if (searchText.value.isNotEmpty()) {
            Image(
                painterResource(id = R.drawable.ic_x_circle),
                contentDescription = "검색중 나오는 x 이미지",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 10.dp, top = 5.dp)
                    .bounceClick {
                        searchText.value = ""
                    }
            )
        }

        Image(
            painter =
            if(isTextFieldSearchFocused.value) painterResource(id = R.drawable.ic_search_in_text_sky)
            else painterResource(id = R.drawable.ic_search_in_text_gray),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 14.dp)
        )
    }
}
