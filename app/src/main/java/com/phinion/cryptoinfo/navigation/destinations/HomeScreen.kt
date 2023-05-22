package com.phinion.cryptoinfo.navigation.destinations


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.phinion.cryptoinfo.R
import com.phinion.cryptoinfo.components.PagerLayout
import com.phinion.cryptoinfo.components.ViewPagerSlider
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import com.phinion.cryptoinfo.ui.theme.appPrimaryColor
import com.phinion.cryptoinfo.ui.theme.poppins_semiBold
import com.phinion.cryptoinfo.ui.theme.softRed
import com.phinion.cryptoinfo.ui.viewmodels.MainViewModel
import com.phinion.cryptoinfo.utils.ApiState


@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
) {



    when (val result = mainViewModel.response.value) {
        is ApiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(
                        rememberNestedScrollInteropConnection()
                    )
            ) {

                    Box(modifier = Modifier
                        .height(56.dp)
                        .fillMaxWidth()
                        .background(appPrimaryColor),
                        contentAlignment = Alignment.Center){
                        Text(text = stringResource(id = R.string.app_name), color = Color.White, fontFamily = poppins_semiBold, textAlign = TextAlign.Center,
                            fontSize = 18.sp)
                    }


                Box(contentAlignment = Alignment.TopCenter) {
                    ViewPagerSlider()
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    result.data.body()?.data?.let {
                        items(it.cryptoCurrencyList) { model ->
                            CryptoInfoLayout(cryptoInfoItem = model)

                        }
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))



                PagerLayout(
                    pageTitleList = listOf("Top Gainers", "Top Losers"),
                    mainViewModel = mainViewModel,
                    navController = navController
                )



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

@Composable
fun CryptoInfoLayout(
    cryptoInfoItem: CryptoCurrency
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        backgroundColor = appPrimaryColor,
        shape = RoundedCornerShape(12.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = "https://s2.coinmarketcap.com/static/img/coins/200x200/" + cryptoInfoItem.id + ".png",
                contentDescription = cryptoInfoItem.name,
                modifier = Modifier.size(50.dp),
                placeholder = painterResource(id = R.drawable.loading)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "%.2f".format(cryptoInfoItem.quotes[0].percentChange24h) + "%",
                    textAlign = TextAlign.Start,
                    fontFamily = poppins_semiBold,
                    color = setColorForPercentage(cryptoInfoItem.quotes[0].percentChange24h),
                    modifier = Modifier.width(70.dp)

                )


                Text(
                    text = cryptoInfoItem.name,
                    textAlign = TextAlign.Start,
                    fontFamily = poppins_semiBold,
                    color = Color.White

                )


            }
        }

    }

}

fun setColorForPercentage(value: Double): Color {
    return if (value < 0) {
        softRed
    } else {
        Color.Green
    }
}




