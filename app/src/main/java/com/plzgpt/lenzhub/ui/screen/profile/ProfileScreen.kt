package com.plzgpt.lenzhub.ui.screen.profile

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*

import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.api.RetrofitBuilder
import com.plzgpt.lenzhub.api.dto.GetUserInfoResponseDTO
import com.plzgpt.lenzhub.api.dto.SignInResponseDTO
import com.plzgpt.lenzhub.ui.data.Category
import com.plzgpt.lenzhub.ui.route.NAV_ROUTE_SEARCH
import com.plzgpt.lenzhub.ui.screen.main.ProfileInfo
import com.plzgpt.lenzhub.ui.screen.search.SearchCategoryFreeScreen
import com.plzgpt.lenzhub.ui.screen.search.SearchCategoryLikeScreen
import com.plzgpt.lenzhub.ui.screen.search.SearchCategoryPayScreen
import com.plzgpt.lenzhub.ui.theme.LHBackground
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHGray
import com.plzgpt.lenzhub.ui.theme.LHMainBackground
import com.plzgpt.lenzhub.util.addFocusCleaner
import com.plzgpt.lenzhub.util.bounceClick
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@Preview
@Composable
fun ProfileScreen(userIdx : Int) {
    val mContext = LocalContext.current

    val isUserInfo = remember { mutableStateOf(false) }


    RetrofitBuilder.userAPI.userProfile(userIdx)
        .enqueue(object : Callback<GetUserInfoResponseDTO> {
            override fun onResponse(
                call: Call<GetUserInfoResponseDTO>,
                response: Response<GetUserInfoResponseDTO>
            ) {
                if (response.isSuccessful) {
                    val res = response.body()
                    if (res != null) {
                        if (res.isSuccess) {
                            isUserInfo.value = true
                            Log.d("userInfo","성공")


                        }
                    }
                }
            }
            override fun onFailure(
                call: Call<GetUserInfoResponseDTO>,
                t: Throwable
            ) {
                Toast.makeText(mContext, "서버가 응답하지 않아요", Toast.LENGTH_SHORT).show()
            }

        })

    var myId = userIdx
    var filter = 10
    var like = 12
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 40.dp)
    ) {
        MainScreen(myId,filter, like)
    }
}


@Composable
fun MainScreen(id: Int, filter: Int, like: Int){
    Column(){

        Text(text = "프로필", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 18.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 25.dp, end = 25.dp, bottom = 20.dp)
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileInfo(id, 1)
        }
        FilterLike(filter, like)
        Pager()
    }

}


@Composable
fun FilterLike(filter : Int, like : Int){

    Row(modifier = Modifier.padding(top = 0.dp, start = 37.dp, end = 37.dp, bottom = 13.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Row(        verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.bounceClick {  }
        ){
            Surface(
                modifier = Modifier.size(25.dp),
                shape = CircleShape
            ) {
                Image(painterResource(id = R.drawable.ic_filter), contentDescription = "")
            }
            Spacer(Modifier.width(4.dp))
            Text(
                "필터 : $filter",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color(0xFF75807A)
                ))
        }
        Spacer(Modifier.width(35.dp))
        Row(        verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.bounceClick {  }

        ){
            Spacer(Modifier.width(4.dp))
            Text(
                "좋아요 : $filter",
                style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color(0xFF75807A)
                ))
        }


    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pager(){
    val titles = listOf("제작 필터", "저장 필터", "좋아요")
    val pagerState = rememberPagerState()
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .addFocusCleaner(focusManager)
    ) {

            Column(modifier = Modifier.fillMaxSize()) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    backgroundColor = Color.Transparent,
                    contentColor = Color.Black,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            color = Color.Black,
                            modifier = Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(start = 18.dp, end= 18.dp)
                ) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            text = { Text(title,
                                    fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,) },
                            selected = pagerState.currentPage == index,
                            onClick = { scope.launch {
                                pagerState.scrollToPage(index)
                            }},
                            modifier = Modifier.height(48.dp).bounceClick {  }
                        )
                    }
                }
                // 내 아이디
                var myId = 77

                //포스트, 큐레이션 텝 레이아웃
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize(),
                    count = 3,
                    state = pagerState
                ) { page ->
                    when (page) {
                        //나중에 API로 받은 값(List)도 넣어줘야할듯
                        0 -> SearchCategoryFreeScreen(Category.PERSON)
                        1 -> SearchCategoryPayScreen(Category.ANIMAL)
                        2 -> SearchCategoryLikeScreen(myId)
                    }

                }
            }


    }
}