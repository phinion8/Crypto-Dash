package com.phinion.cryptoinfo.navigation.destinations

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.phinion.cryptoinfo.GainLossLayout
import com.phinion.cryptoinfo.components.GetGainOrLossList
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import com.phinion.cryptoinfo.ui.theme.*
import com.phinion.cryptoinfo.ui.viewmodels.MainViewModel
import com.phinion.cryptoinfo.utils.ApiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun MarketScreen(
    mainViewModel: MainViewModel,
    navController: NavController
) {

    val textSearch by mainViewModel.textSearch.collectAsState()


    when (val result = mainViewModel.response.value) {
        is ApiState.Success -> {

            var cryptoListItems: ArrayList<CryptoCurrency> =
                result.data.body()?.data?.cryptoCurrencyList as ArrayList<CryptoCurrency>

            var updatedList: ArrayList<CryptoCurrency> = ArrayList()







            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                val scope = rememberCoroutineScope()

                Box(modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth()
                    .background(appPrimaryColor),
                contentAlignment = Alignment.Center){
                    Text(text = "Market", color = Color.White, fontFamily = poppins_semiBold, textAlign = TextAlign.Center,
                    fontSize = 18.sp)
                }

                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                shape = RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp),
                    elevation = 16.dp,
                    backgroundColor = Color.White
                ) {
                    Box(modifier = Modifier.fillMaxWidth(0.85f).height(65.dp), contentAlignment = Alignment.Center){
                        TextField(
                            value = textSearch, onValueChange = {
                                mainViewModel.setSearchText(it)

                            }, label = {
                                Text(
                                    text = "Search coins",
                                    fontFamily = poppins_medium,
                                    modifier = Modifier.padding(0.dp),
                                    textAlign = TextAlign.Center,
                                    fontSize = 14.sp
                                )
                            }, modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(55.dp)
                                .border(
                                    width = 2.dp,
                                    shape = RoundedCornerShape(50.dp),
                                    color = appPrimaryColor
                                )
                                .background(shape = RoundedCornerShape(50.dp), color = gray),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                imeAction = ImeAction.Search
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    scope.launch {
                                        mainViewModel.textSearch.collect {

                                            updatedList = updateCryptoList(
                                                it,
                                                cryptoListItems
                                            ) as ArrayList<CryptoCurrency>
                                            Log.d("DBPS", updatedList.size.toString())

                                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                                "cryptoList",
                                                updatedList
                                            )

                                            navController.navigate("searchList")

                                        }
                                    }

                                }
                            ),
                            textStyle = TextStyle(fontFamily = poppins_regular, fontSize = 14.sp),
                            leadingIcon = {
                                androidx.compose.material.Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "search",
                                    tint = appPrimaryColor
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                leadingIconColor = appPrimaryColor,
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.Transparent
                            )
                        )
                    }

                }



                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cryptoListItems) { model ->

                        GainLossLayout(cryptoCurrency = model, onClick = {

                            navController.currentBackStackEntry?.savedStateHandle?.set(
                                "cryptoDetail",
                                model
                            )
                            navController.navigate("cryptoDetail")

                        })

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


private fun updateCryptoList(
    searchQuery: String,
    cryptoCurrencyList: List<CryptoCurrency>
): List<CryptoCurrency> {
    val updatedList: ArrayList<CryptoCurrency> = ArrayList()
    for (item in cryptoCurrencyList) {
        val coinName = item.name.lowercase()
        val coinSymbol = item.symbol.lowercase()

        if (coinName.contains(searchQuery) || coinSymbol.contains(searchQuery)) {
            updatedList.add(item)
        }
    }

    return updatedList
}

