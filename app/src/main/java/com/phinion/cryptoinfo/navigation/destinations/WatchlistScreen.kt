package com.phinion.cryptoinfo.navigation.destinations

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.phinion.cryptoinfo.GainLossLayout
import com.phinion.cryptoinfo.components.PagerLayout
import com.phinion.cryptoinfo.components.ViewPagerSlider
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import com.phinion.cryptoinfo.ui.theme.appPrimaryColor
import com.phinion.cryptoinfo.ui.theme.poppins_medium
import com.phinion.cryptoinfo.ui.theme.poppins_semiBold
import com.phinion.cryptoinfo.ui.viewmodels.MainViewModel
import com.phinion.cryptoinfo.utils.ApiState

@Composable
fun WatchlistScreen(
    mainViewModel: MainViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val watchList: ArrayList<String>? = readData(context)

    when (val result = mainViewModel.response.value) {
        is ApiState.Success -> {
            Column(modifier = Modifier.fillMaxSize()) {
                var watchListItem: ArrayList<CryptoCurrency> = ArrayList()


                if (watchList != null) {
                    for (watchData in watchList) {

                        for (item in result.data.body()?.data?.cryptoCurrencyList!!) {
                            if (watchData == item.symbol) {
                                watchListItem.add(item)
                            }
                        }

                    }
                }




                Box(
                    modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(appPrimaryColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Watchlist",
                        color = Color.White,
                        fontFamily = poppins_semiBold,
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (watchList != null) {
                    if (watchList.isNotEmpty()) {

                        LazyColumn(
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {


                            items(watchListItem) { model ->
                                GainLossLayout(cryptoCurrency = model, onClick = {

                                    navController.currentBackStackEntry?.savedStateHandle?.set(
                                        "cryptoDetail",
                                        model
                                    )
                                    navController.navigate("cryptoDetail")

                                })

                            }


                        }


                    } else {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            Text(
                                text = "Nothing here try adding\nsome cryptocurrency",
                                fontFamily = poppins_medium,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }


            }


        }

        is ApiState.Failure -> {
            Text(text = "Failed")
        }

        is ApiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        }

        is ApiState.Empty -> {

        }
    }
}

private fun readData(context: Context): ArrayList<String>? {
    //Reading data
    var watchList: ArrayList<String>? = null
    val sharedPreferences = context.getSharedPreferences("watchlist", Context.MODE_PRIVATE)
    val json = sharedPreferences.getString("watchlist", ArrayList<String>().toString())
    val type = object : TypeToken<ArrayList<String>>() {}.type
    watchList = Gson().fromJson(json, type)
    return watchList
}