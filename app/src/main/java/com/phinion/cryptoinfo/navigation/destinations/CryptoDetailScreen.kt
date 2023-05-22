package com.phinion.cryptoinfo.navigation.destinations

import android.content.Context
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.phinion.cryptoinfo.R
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import com.phinion.cryptoinfo.models.crptoModels.Quote
import com.phinion.cryptoinfo.ui.theme.appPrimaryColor
import com.phinion.cryptoinfo.ui.theme.poppins_regular
import com.phinion.cryptoinfo.ui.theme.poppins_semiBold
import com.phinion.cryptoinfo.utils.Constants
import okhttp3.internal.format
import kotlin.text.Typography.quote

@Composable
fun CryptoDetails(
    cryptoCurrency: CryptoCurrency,
    navController: NavController
) {
    var duration by remember {
        mutableStateOf("15")
    }
    var tint by remember {
        mutableStateOf(Color.White)
    }

    var watchList: ArrayList<String>? = null
    var watchListIsChecked = false

    val context = LocalContext.current

    watchList = readData(context)

    watchListIsChecked = watchList!!.contains(cryptoCurrency.symbol)

    if (watchListIsChecked){
        tint = Color.Yellow
    }else{
        tint = Color.White
    }



    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(appPrimaryColor),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = {
                navController.navigate(route = "home") {
                    //excluding home pop up all the screen from the back stack
                    popUpTo("home") {
                        inclusive = true
                    }
                }

            }) {

                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = "Back arrow",
                    tint = Color.White
                )


            }


            Text(
                text = cryptoCurrency.name,
                fontFamily = poppins_semiBold,
                fontSize = 18.sp,
                color = Color.White
            )

            IconButton(onClick = {

                watchListIsChecked = if(!watchListIsChecked){
                    if (!watchList.contains(cryptoCurrency.symbol)){
                        watchList.add(cryptoCurrency.symbol)
                        tint = Color.Yellow
                        Toast.makeText(context, cryptoCurrency.name + " added to watchlist", Toast.LENGTH_SHORT).show()
                    }
                    storeData(context, watchList)
                    true
                }else{
                    watchList.remove(cryptoCurrency.symbol)
                    tint = Color.White
                    Toast.makeText(context, cryptoCurrency.name +" removed from watchlist", Toast.LENGTH_SHORT).show()
                    storeData(context, watchList)
                    false
                }


            }) {
                Icon(
                    imageVector = Icons.Default.Star, contentDescription = "watchlist icon",
                    tint = tint
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                AsyncImage(
                    model = Constants.BASE_PHOTO_URL + cryptoCurrency.id + ".png",
                    contentDescription = cryptoCurrency.name,
                    placeholder = painterResource(id = R.drawable.loading),
                    modifier = Modifier.size(70.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {

                    Text(
                        text = "%.2f".format(cryptoCurrency.quotes[0].price) + "$",
                        fontFamily = poppins_regular,
                        fontSize = 24.sp,
                        color = appPrimaryColor
                    )



                    Text(
                        text = "%.2f".format(cryptoCurrency.quotes[0].percentChange24h) + "%",
                        fontFamily = poppins_semiBold,
                        fontSize = 18.sp,
                        color = appPrimaryColor
                    )

                }


            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                OutlinedButton(onClick = {

                    duration = "15"

                }) {
                    Text(text = "15 Min", fontFamily = poppins_regular, color = appPrimaryColor)
                }
                OutlinedButton(onClick = {

                    duration = "1H"


                }) {
                    Text(text = "1 Hour", fontFamily = poppins_regular, color = appPrimaryColor)
                }
                OutlinedButton(onClick = {

                    duration = "4H"

                }) {
                    Text(text = "4 Hour", fontFamily = poppins_regular, color = appPrimaryColor)
                }
                OutlinedButton(onClick = {

                    duration = "D"

                }) {
                    Text(text = "1 Day", fontFamily = poppins_regular, color = appPrimaryColor)
                }
                OutlinedButton(onClick = {

                    duration = "W"

                }) {
                    Text(text = "1 Week", fontFamily = poppins_regular, color = appPrimaryColor)
                }
                OutlinedButton(onClick = {

                    duration = "M"

                }) {
                    Text(text = "1 Mon", fontFamily = poppins_regular, color = appPrimaryColor)
                }

                Spacer(modifier = Modifier.width(4.dp))
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                CryptoWebInfo(cryptoCurrency = cryptoCurrency, duration = duration)
            }

            CryptoDetailInfo(key = "Name", value = cryptoCurrency.name)
            CryptoDetailInfo(
                key = "Price In USD",
                value = "$%.3f".format(cryptoCurrency.quotes[0].price)
            )
            CryptoDetailInfo(key = "Last Updated", value = cryptoCurrency.quotes[0].lastUpdated)
            CryptoDetailInfo(
                key = "Percentage Change 1h",
                value = "%.4f".format(cryptoCurrency.quotes[0].percentChange1h)
            )
            CryptoDetailInfo(
                key = "Percentage Change 24h",
                value = "%.4f".format(cryptoCurrency.quotes[0].percentChange24h)
            )
            CryptoDetailInfo(
                key = "Percentage Change 30d",
                value = "%.4f".format(cryptoCurrency.quotes[0].percentChange30d)
            )
            CryptoDetailInfo(
                key = "Percentage Change 7d",
                value = "%.4f".format(cryptoCurrency.quotes[0].percentChange7d)
            )
            CryptoDetailInfo(
                key = "Percentage Change 60d",
                value = "%.4f".format(cryptoCurrency.quotes[0].percentChange60d)
            )
            CryptoDetailInfo(
                key = "Percentage Change 90d",
                value = "%.4f".format(cryptoCurrency.quotes[0].percentChange90d)
            )

        }


    }


}

@Composable
fun CryptoDetailInfo(
    key: String,
    value: String
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = key, fontFamily = poppins_regular, color = appPrimaryColor)
        Text(text = value, fontFamily = poppins_regular, color = appPrimaryColor)
    }
}

@Composable
fun CryptoWebInfo(
    cryptoCurrency: CryptoCurrency,
    duration: String
) {

    val url =
        "https://s.tradingview.com/widgetembed/?frameElementId=trandingview_76d87&symbol=" + cryptoCurrency.symbol + "USD&interval=$duration&hidesidetoolbar=1&hidetoptoolbar=1&symboledit=1&saveimage=1&toolbarbg=F1F3F6&studies=[]&hideideas=1&theme=Dark&style=1&timezone=Etc%2FUTC&studies_overrides={}&overrides={}&enabled_features=[]&disabled_features=[]&locale=en&utm_source=coinmarketcap.com&utm_medium=widget&utm_campaign=chart&utm_term=BTCUSDT"

    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.allowContentAccess = true
            loadUrl(url)
        }
    }, update = {
        it.loadUrl(url)
    })

}

private fun storeData(context: Context, watchList: ArrayList<String>) {
    //Storing Data
    val sharedPreferences = context.getSharedPreferences("watchlist", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    val json1 = Gson().toJson(watchList)
    editor.putString("watchlist", json1)
    editor.apply()
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
