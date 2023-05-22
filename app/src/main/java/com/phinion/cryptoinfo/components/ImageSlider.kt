package com.phinion.cryptoinfo.components

import android.graphics.PorterDuff
import android.widget.RatingBar
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.accompanist.pager.*
import com.phinion.cryptoinfo.R
import com.phinion.cryptoinfo.models.sliderList
import com.phinion.cryptoinfo.ui.theme.Purple500
import com.phinion.cryptoinfo.ui.theme.appPrimaryColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue


@ExperimentalPagerApi
@Composable
fun ViewPagerSlider(){

    val pagerState  = rememberPagerState(
        initialPage =  2
    )

    LaunchedEffect(Unit){
        while (true){
            yield()
            delay(2000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount),
                animationSpec = tween(600)
            )
        }
    }

    Column(modifier = Modifier.height(200.dp).fillMaxWidth()) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(color = Purple500),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

        }

        Spacer(modifier = Modifier.height(4.dp))
        HorizontalPager(state = pagerState,
            count = sliderList.size,
            modifier = Modifier
                .weight(1f)
                .padding(0.dp, 16.dp, 0.dp, 16.dp)
        ) { page ->

            Card(modifier = Modifier
                .graphicsLayer {

                    val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue

                }
                .fillMaxWidth()
                .padding(16.dp, 0.dp, 16.dp, 0.dp),
                shape = RoundedCornerShape(16.dp)
            ) {

                val sliderItem = sliderList[page]
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)
                    .align(Alignment.CenterHorizontally)
                ) {
                    Image(painter = painterResource(
                        id = sliderItem.image
                    ),
                        contentDescription = "Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Column(modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(15.dp)
                    ) {
                        Text(
                            text = sliderItem.title,
                            style = MaterialTheme.typography.h5,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = sliderItem.description,
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.padding(0.dp,8.dp,0.dp,0.dp)
                        )


                    }

                }


            }

        }

        //Horizontal dot indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(8.dp)
        )

    }

}