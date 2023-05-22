package com.phinion.cryptoinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.phinion.cryptoinfo.models.crptoModels.CryptoCurrency
import com.phinion.cryptoinfo.navigation.destinations.setColorForPercentage
import com.phinion.cryptoinfo.ui.theme.appPrimaryColor
import com.phinion.cryptoinfo.ui.theme.poppins_regular
import com.phinion.cryptoinfo.ui.theme.poppins_semiBold
import com.phinion.cryptoinfo.ui.viewmodels.MainViewModel
import com.phinion.cryptoinfo.utils.Constants.BASE_PHOTO_URL
import com.phinion.cryptoinfo.utils.Constants.CRYPTO_GRAPH_URL

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GainLossLayout(
    onClick : () -> Unit,
    cryptoCurrency: CryptoCurrency
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 8.dp,
        shape = RoundedCornerShape(16.dp),
        backgroundColor = appPrimaryColor,
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Spacer(modifier = Modifier.width(4.dp))
            AsyncImage(
                model = BASE_PHOTO_URL + cryptoCurrency.id + ".png",
                contentDescription = cryptoCurrency.name,
                placeholder = painterResource(id = R.drawable.loading),
                modifier = Modifier
                    .size(45.dp)
                    .weight(2f)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))

            Column(modifier = Modifier.weight(3f)) {
                Text(
                    text = cryptoCurrency.name,
                    textAlign = TextAlign.Start,
                    fontFamily = poppins_semiBold,
                    color = Color.White,
                    maxLines = 1
                )
                Text(
                    text = cryptoCurrency.symbol,
                    textAlign = TextAlign.Start,
                    fontFamily = poppins_regular,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.width(4.dp))
            Column(modifier = Modifier.weight(4f)) {
                Text(
                    text = "%.2f".format(cryptoCurrency.quotes[0].price) + "$",
                    textAlign = TextAlign.Start,
                    fontFamily = poppins_regular,
                    color = Color.White
                )
                Text(
                    text = "%.4f".format(cryptoCurrency.quotes[0].percentChange24h)+ "%",
                    textAlign = TextAlign.Start,
                    fontFamily = poppins_regular,
                    color = setColorForPercentage(cryptoCurrency.quotes[0].percentChange24h)
                )

            }

            AsyncImage(
                model = CRYPTO_GRAPH_URL + cryptoCurrency.id + ".png",
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.loading),
                modifier = Modifier
                    .size(70.dp)
                    .weight(4f)
                    .padding(paddingValues = PaddingValues(end = 8.dp))
            )


        }
    }
}