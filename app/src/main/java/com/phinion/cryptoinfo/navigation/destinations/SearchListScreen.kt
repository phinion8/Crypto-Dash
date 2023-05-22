package com.phinion.cryptoinfo.navigation.destinations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.phinion.cryptoinfo.GainLossLayout
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import com.phinion.cryptoinfo.ui.theme.appPrimaryColor
import com.phinion.cryptoinfo.ui.theme.poppins_regular
import com.phinion.cryptoinfo.ui.theme.poppins_semiBold

@Composable
fun CryptoLazyList(
    navController: NavController,
    cryptoListItems: List<CryptoCurrency>
) {






    Column(modifier = Modifier.fillMaxSize()) {
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
                text = "Search",
                fontFamily = poppins_semiBold,
                fontSize = 18.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.width(56.dp))


        }

        Spacer(modifier = Modifier.height(16.dp))

        if (cryptoListItems.isNotEmpty()){

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {


                items(items = cryptoListItems, key = { cryptoItem ->
                    cryptoItem.id

                }) { model ->
                    GainLossLayout(cryptoCurrency = model, onClick = {

                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            "cryptoDetail",
                            model
                        )
                        navController.navigate("cryptoDetail")

                    })

                }
            }
        }else{
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = "Not found", fontFamily = poppins_regular)
            }

        }


    }


}