package com.phinion.cryptoinfo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import com.phinion.cryptoinfo.navigation.destinations.*
import com.phinion.cryptoinfo.ui.viewmodels.MainViewModel

@Composable
fun Navigation(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    NavHost(navController = navController, startDestination = "home") {

        composable(route = "home") {
            HomeScreen(mainViewModel = mainViewModel, navController = navController)
        }

        composable(route = "market") {
            MarketScreen(mainViewModel = mainViewModel, navController = navController)
        }

        composable(route = "watchlist") {
            WatchlistScreen(mainViewModel = mainViewModel, navController = navController)
        }

        composable(route = "cryptoDetail"){

            val result = navController.previousBackStackEntry?.savedStateHandle?.get<CryptoCurrency>("cryptoDetail")
            if (result != null) {
                CryptoDetails(navController = navController, cryptoCurrency = result)
            }
        }

        composable(route = "searchList"){

            val result = navController.previousBackStackEntry?.savedStateHandle?.get<List<CryptoCurrency>>("cryptoList")
            if (result != null){
                CryptoLazyList(navController = navController, cryptoListItems = result)
            }

        }

    }

}