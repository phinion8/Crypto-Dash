package com.phinion.cryptoinfo.models

import com.phinion.cryptoinfo.R

data class SliderItem(
    val image: Int,
    val title: String = "",
    val description: String = ""
)

val sliderList = listOf(
    SliderItem(R.drawable.crypto_currency_image_1, "", ""),
    SliderItem(R.drawable.crypto_currency_image_2, "", "")
)