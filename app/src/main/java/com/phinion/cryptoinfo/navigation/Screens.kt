package com.phinion.cryptoinfo.navigation

import androidx.navigation.NavHostController

class Screens(navController: NavHostController){

    val splash: () -> Unit = {
        navController.navigate(route = "main")
    }

    val mainScreen: () -> Unit = {
        navController.navigate(route = "market")
    }


}