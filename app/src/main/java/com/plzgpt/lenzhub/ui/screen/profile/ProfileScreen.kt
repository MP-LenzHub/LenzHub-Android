package com.plzgpt.lenzhub.ui.screen.profile
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.plzgpt.lenzhub.ApplicationClass
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.api.dto.FollowRequestBody
import com.plzgpt.lenzhub.ui.screen.lenz.viewmodel.UserViewModel
import com.plzgpt.lenzhub.ui.screen.main.ProfileInfo
import com.plzgpt.lenzhub.ui.screen.search.SearchCategoryLikeScreen
import com.plzgpt.lenzhub.util.PostHeartCard
import com.plzgpt.lenzhub.util.addFocusCleaner
import com.plzgpt.lenzhub.util.bounceClick
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen(
    userIdx : Int,
) {
    var viewModel = UserViewModel()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 40.dp)
    ) {
        MainScreen(userIdx, viewModel)
    }
}


@Composable
fun MainScreen(userIdx: Int, viewModel : UserViewModel){
    val profileState by viewModel.profileState.collectAsState()
    val isLiked = remember { mutableStateOf(false) } // 좋아요 했는지 여부

    val myId = ApplicationClass.sharedPreferences.getInt(ApplicationClass.clientId, 0)

    viewModel.getProfile(userIdx)


    Column(){

        Text(text = "프로필", fontSize = 22.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 18.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 25.dp, end = 25.dp, bottom = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileInfo(
                    userIdx = userIdx,
                    mode = 1,
                    userName = profileState.name,
                    userImage = profileState.profileImgUrl,
                    grade = ""
                )
                if (myId != userIdx) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .bounceClick {
                                viewModel.postFollow(userIdx, myId)
                            }

                    ) {
                        PostHeartCard(
                            modifier = Modifier,
                            heartState = isLiked,
                        )
                    }
                }
            }

        }
        FilterLike(profileState.createPosts.postList.size, profileState.likedCounts)
        Pager(userIdx,viewModel)
    }

}


@Composable
fun FilterLike(filter : Int, like : Int){

    Row(modifier = Modifier
        .padding(top = 0.dp, start = 37.dp, end = 37.dp, bottom = 13.dp)
        .fillMaxWidth(),
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
                "저장 필터 수 : $like",
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
fun Pager(userIdx:Int, viewModel: UserViewModel){
    val titles = listOf("제작 필터", "저장 필터", "좋아요")
    val pagerState = rememberPagerState()
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val profileState by viewModel.profileState.collectAsState()
    val followerState by viewModel.followerState.collectAsState()

    viewModel.getFollowInfo(userIdx)
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
                        .padding(start = 18.dp, end = 18.dp)
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
                            modifier = Modifier
                                .height(48.dp)
                                .bounceClick { }
                        )
                    }
                }

                //포스트, 큐레이션 텝 레이아웃
                HorizontalPager(
                    modifier = Modifier
                        .fillMaxSize(),
                    count = 3,
                    state = pagerState
                ) { page ->
                    when (page) {
                        //나중에 API로 받은 값(List)도 넣어줘야할듯
                        0 -> MyLenzScreen(profileState.createPosts)
                        1 -> MySaveScreen(profileState.likedPosts)
                        2 -> SearchCategoryLikeScreen(followerState)
                    }

                }
            }


    }
}