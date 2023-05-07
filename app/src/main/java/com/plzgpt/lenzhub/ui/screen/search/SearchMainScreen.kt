package com.plzgpt.lenzhub.ui.screen.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.plzgpt.lenzhub.R
import com.plzgpt.lenzhub.ui.data.Category
import com.plzgpt.lenzhub.ui.route.NAV_ROUTE_SEARCH
import com.plzgpt.lenzhub.ui.theme.LHBackground
import com.plzgpt.lenzhub.ui.theme.LHBlack
import com.plzgpt.lenzhub.ui.theme.LHGray
import com.plzgpt.lenzhub.util.ModalBottomSheet
import com.plzgpt.lenzhub.util.ModalBottomSheetItem
import com.plzgpt.lenzhub.util.addFocusCleaner
import com.plzgpt.lenzhub.util.bounceClick
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun SearchMainScreen(navController: NavHostController) {
    val pagerState = rememberPagerState()

    val selectedCategory = remember { mutableStateOf(Category.ANIMAL) }


    val focusManager = LocalFocusManager.current

    val showModalSheet = rememberSaveable{ mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        title = "카테고리",
        bottomSheetState = bottomSheetState,
        sheetScreen = {
            ModalBottomSheetItem(text = "동물", icon = R.drawable.ic_animal, trailing = true, modifier = Modifier.bounceClick {
                selectedCategory.value = Category.ANIMAL

                scope.launch {
                    bottomSheetState.hide()
                }
            })
            ModalBottomSheetItem(text = "인물", icon = R.drawable.ic_person, trailing = true, modifier = Modifier.bounceClick {

                selectedCategory.value = Category.PERSON

                scope.launch {
                    bottomSheetState.hide()
                }
            })
            ModalBottomSheetItem(text = "풍경", icon = R.drawable.ic_sight, trailing = true, modifier = Modifier.bounceClick {
                selectedCategory.value = Category.SIGHT

                scope.launch {
                    bottomSheetState.hide()
                }
            })
            ModalBottomSheetItem(text = "음식", icon = R.drawable.ic_food, trailing = true, modifier = Modifier.bounceClick {
                selectedCategory.value = Category.FOOD

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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .addFocusCleaner(focusManager)
            ) {

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {

                    //상단 카테고리 선택 및 검색버튼 있는 레이아웃
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(start = 18.dp, top = 40.dp, end = 18.dp, bottom = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "탐색",
                            color = Color.Black,
                            fontSize = 22.sp,
                            fontWeight = FontWeight(700)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Image(
                            painter = painterResource(id = R.drawable.ic_search_big),
                            contentDescription = null,
                            modifier = Modifier
                                .bounceClick {
                                    navController.navigate(route = NAV_ROUTE_SEARCH.DISCOVERSEARCHING.routeName)
                                }

                        )
                    }
                    Row(
                        modifier = Modifier
                            .height(36.dp)
                            .fillMaxWidth()
                            .padding(end = 18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Row(
                            Modifier
                                .padding(horizontal = 18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "무료필터",
                                fontSize = 20.sp,
                                color =
                                if(pagerState.currentPage == 0) LHBlack
                                else LHGray,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .bounceClick {
                                        scope.launch {
                                            pagerState.animateScrollToPage(0)
                                        }
                                    })
                            Spacer(Modifier.size(20.dp))
                            Text(text = "유료필터",
                                fontSize = 20.sp,
                                color =
                                if(pagerState.currentPage == 1) LHBlack
                                else LHGray,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.bounceClick {
                                    scope.launch {
                                        pagerState.animateScrollToPage(1)
                                    }

                                }
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        Box(
                            modifier = Modifier
                                .bounceClick {
                                    focusManager.clearFocus()
                                    showModalSheet.value = !showModalSheet.value
                                    scope.launch {
                                        bottomSheetState.show()
                                    }
                                }
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(10.dp))
                                .background(color = LHBackground),

                            ) {
                            Row(
                                modifier = Modifier.fillMaxHeight(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 13.dp),
                                    text = selectedCategory.value.category,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight(600),
                                        color = LHBlack
                                    )
                                )
                                //카테고리 옆에 있는 ^ 이거 거꾸로 이미지
                                Image(
                                    modifier = Modifier.padding(start = 5.dp, end = 12.dp),
                                    painter = painterResource(id = R.drawable.ic_chevron_down_new),
                                    contentDescription = null,
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(14.dp))

                    //포스트, 큐레이션 텝 레이아웃
                    HorizontalPager(
                        modifier = Modifier
                            .fillMaxSize(),
                        count = 2,
                        state = pagerState
                    ) { page ->
                        when (page) {
                            //나중에 API로 받은 값(List)도 넣어줘야할듯
                            0 -> SearchCategoryFreeScreen(selectedCategory.value)
                            1 -> SearchCategoryPayScreen(selectedCategory.value)
                        }

                    }

                }
            }

        }
    )



}
