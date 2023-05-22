package com.phinion.cryptoinfo.components

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.phinion.cryptoinfo.GainLossLayout
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import com.phinion.cryptoinfo.navigation.destinations.CryptoInfoLayout
import com.phinion.cryptoinfo.navigation.destinations.setColorForPercentage
import com.phinion.cryptoinfo.ui.theme.appPrimaryColor
import com.phinion.cryptoinfo.ui.theme.poppins_semiBold
import com.phinion.cryptoinfo.ui.viewmodels.MainViewModel
import com.phinion.cryptoinfo.utils.ApiState
import kotlinx.coroutines.launch
import java.util.Collections

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerLayout(
    navController: NavController,
    mainViewModel: MainViewModel,
    pageTitleList: List<String>
) {


    var pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()





        Card(
            elevation = 8.dp,
            modifier = Modifier
                .padding(all = 16.dp)
                .nestedScroll(rememberNestedScrollInteropConnection()),
            shape = RoundedCornerShape(30.dp)
        ) {

            TabRow(
                // Our selected tab is our current page
                selectedTabIndex = pagerState.currentPage,
                backgroundColor = Color.White,
                modifier = Modifier
                    .background(Color.White)
                    .clip(RoundedCornerShape(30.dp)),
                // Override the indicator, using the provided pagerTabIndicatorOffset modifier
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .pagerTabIndicatorOffset(pagerState, tabPositions)
                            .width(0.dp)
                            .height(0.dp)
                    )
                }
            ) {
                // Add tabs for all of our pages
                pageTitleList.forEachIndexed { index, title ->
                    val color = remember {
                        Animatable(Color.White)
                    }

                    LaunchedEffect(key1 = pagerState.currentPage == index) {
                        color.animateTo(if (pagerState.currentPage == index) appPrimaryColor else Color.White)
                    }
                    Tab(
                        text = {
                            Text(
                                text = title,
                                fontFamily = poppins_semiBold,
                                color = if (pagerState.currentPage == index) Color.White else appPrimaryColor
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        modifier = Modifier.background(
                            color = color.value,
                            shape = RoundedCornerShape(30.dp)
                        )
                    )

                }

            }

        }








    HorizontalPager(
        count = pageTitleList.size,
        state = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .nestedScroll(rememberNestedScrollInteropConnection())
    ) { page ->


        if (page == 0) {
            GetGainOrLossList(
                pagePosition = 0,
                mainViewModel = mainViewModel,
                navController = navController
            )
        } else {
            GetGainOrLossList(
                pagePosition = 1,
                mainViewModel = mainViewModel,
                navController = navController
            )

        }


    }

}

@Composable
fun GetGainOrLossList(
    navController: NavController,
    pagePosition: Int,
    mainViewModel: MainViewModel
) {
    when (val result = mainViewModel.topGainLossResponse.value) {
        is ApiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                val cryptoList = result.data.body()?.data?.cryptoCurrencyList
                if (cryptoList != null) {
                    Collections.sort(cryptoList) { o1, o2 ->

                        (o2.quotes[0].percentChange24h.toInt()
                            .compareTo(o1.quotes[0].percentChange24h.toInt()))

                    }
                    val topGainersList = ArrayList<CryptoCurrency>()

                    if (pagePosition == 0) {
                        topGainersList.clear()
                        for (i in 0..9) {
                            topGainersList.add(cryptoList[i])
                        }

                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.background(color = Color.White)
                        ) {

                            items(cryptoList) { model ->


                                GainLossLayout(cryptoCurrency = model, onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "cryptoDetail",
                                        model
                                    )
                                    navController.navigate(route = "cryptoDetail")

                                })


                            }

                        }
                    } else if (pagePosition == 1) {

                        Collections.sort(cryptoList) { o1, o2 ->

                            (o1.quotes[0].percentChange24h.toInt()
                                .compareTo(o2.quotes[0].percentChange24h.toInt()))
                        }
                        val topLosersList = ArrayList<CryptoCurrency>()

                        topLosersList.clear()
                        for (i in 0..9) {
                            topLosersList.add(cryptoList[cryptoList.size - 1 - i])
                        }
                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {

                            items(cryptoList) { model ->

                                GainLossLayout(cryptoCurrency = model, onClick = {
                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "cryptoDetail",
                                        model
                                    )
                                    navController.navigate(route = "cryptoDetail")
                                })

                            }

//                            result.data.body()?.data?.let {
//                                items(it.cryptoCurrencyList) { model ->
//                                    GainLossLayout(cryptoCurrency = model)
//
//                                }
//                            }

                        }
                    }

                }


            }
        }

        is ApiState.Failure -> {
            Text(text = "Failed")
        }

        is ApiState.Loading -> {

        }

        is ApiState.Empty -> {

        }
    }
}

