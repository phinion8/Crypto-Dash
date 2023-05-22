package com.phinion.cryptoinfo.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.phinion.cryptoinfo.models.BottomNavItem
import com.phinion.cryptoinfo.ui.theme.appPrimaryColor
import com.phinion.cryptoinfo.ui.theme.poppins_medium
import com.phinion.cryptoinfo.ui.theme.poppins_regular
import com.phinion.cryptoinfo.ui.theme.poppins_semiBold
import kotlinx.coroutines.selects.select

@Composable
fun BottomNavigationBar(
    bottomNavItems: List<BottomNavItem>,
    navController: NavController,
    modifier: Modifier,
    onItemClick: (BottomNavItem) -> Unit
) {
    //this will give us the current backstack entry as a state and with that backstack entry we get access of the current route
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        backgroundColor = Color.White,
        elevation = 8.dp

    ) {

        bottomNavItems.forEach { item ->

            //checking weather the current route is actually the same as the route we selected
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(selected = selected, onClick = { onItemClick(item) },
                selectedContentColor = appPrimaryColor,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(horizontalAlignment = CenterHorizontally) {
                        if (item.badgeCount > 0) {
                            BadgedBox(
                                badge = {
                                    Text(text = item.badgeCount.toString()) //badge content
                                },
                            ) {
                                Icon(painter = item.icon, contentDescription = item.name)
                            }
                        } else {

                            Icon(painter = item.icon, contentDescription = item.name)

                        }

                                AnimatedVisibility(visible = selected) {
                                    Text(
                                        text = item.name,
                                        textAlign = TextAlign.Center,
                                        fontSize = 12.sp,
                                        fontFamily = poppins_medium
                                    )
                                }


                        }
                }

            )

        }


    }
}


