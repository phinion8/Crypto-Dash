package com.phinion.cryptoinfo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import com.phinion.cryptoinfo.components.BottomNavigationBar
import com.phinion.cryptoinfo.models.BottomNavItem
import com.phinion.cryptoinfo.navigation.Navigation
import com.phinion.cryptoinfo.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()
            Scaffold(bottomBar = {
                BottomNavigationBar(
                    bottomNavItems = listOf(
                        BottomNavItem(
                            "Home",
                            route = "home",
                            icon = painterResource(id = R.drawable.ic_home)
                        ),
                        BottomNavItem(
                            "Market",
                            route = "market",
                            icon = painterResource(id = R.drawable.ic_graph),
                        ),
                        BottomNavItem(
                            "Watchlist",
                            "watchlist",
                            icon = painterResource(id = R.drawable.ic_bookmark)
                        )
                    ),
                    navController = navController,
                    modifier = Modifier.fillMaxWidth(),
                    onItemClick = {
                        navController.navigate(it.route)
                    }
                )
            },
                content = {


                            Navigation(navController = navController, mainViewModel = mainViewModel)



                })
        }
    }



}






