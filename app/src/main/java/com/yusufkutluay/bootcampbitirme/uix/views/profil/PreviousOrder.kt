package com.yusufkutluay.bootcampbitirme.uix.views.profil

import LottieAnimationExample
import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import com.yusufkutluay.bootcampbitirme.R
import com.yusufkutluay.bootcampbitirme.data.entity.Order
import com.yusufkutluay.bootcampbitirme.data.entity.OrderPrevious
import com.yusufkutluay.bootcampbitirme.ui.theme.BasketTextField
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeDark
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeLight
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.PreviousOrderViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviousOrder(
    modifier: Modifier,
    navController: NavHostController,
    currentUserId: String?,
    previousOrderViewModel: PreviousOrderViewModel,
    context: Context
) {

    var listOrderPrevious by remember { mutableStateOf<List<OrderPrevious>>(emptyList()) }
    var listOrder by remember { mutableStateOf<List<Order>>(emptyList()) }

    LaunchedEffect(Unit) {
        previousOrderViewModel.getPreviousOrder(currentUserId!!) { list->
            if (list != null){
                listOrderPrevious = list.reversed()

            }
        }
    }

    Scaffold (
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { (context as Activity).finish() },
                        modifier.padding(start = 10.dp)
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "", tint = ThemeDark)
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.order_myorder),
                        fontSize = 28.sp,
                        color = ThemeDark,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        modifier = modifier
                            .padding()
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ThemeLight
                )
            )
        }
    ){paddingValues ->
        Column(
            modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            /**
             * Siparişlerin yazdığı column yapısı
             */
            if (listOrderPrevious.isNotEmpty()){
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                items(
                    count = listOrderPrevious.reversed().count(),
                    itemContent = {
                        val open = remember { mutableStateOf(false) } // Her kart için ayrı bir `open` state oluşturuluyor.
                        val orderPrevious = listOrderPrevious[it]
                        listOrder = orderPrevious.orders
                        CardOrder(
                            modifier = modifier,
                            date = orderPrevious.orderDay,
                            list = listOrder,
                            hours = orderPrevious.orderTime,
                            total = orderPrevious.orderTotal,
                            open = open
                        )

                    }
                )

            }

            }else{
                Column (
                    modifier
                        .background(Color.White)  // arka plan rengi
                        .wrapContentSize(), // full size
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    LottieAnimationExample(animationResId = R.raw.anim_empty)
                }
            }
        }
    }
}

@Composable
fun CardOrder(
    modifier: Modifier,
    date: String,
    list: List<Order>,
    hours: String,
    total: String,
    open: MutableState<Boolean>
){

    OutlinedCard(
        onClick = {
            open.value = !open.value
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 15.dp),
        colors = CardDefaults.cardColors(
            containerColor = BasketTextField
        )
    ) {
        Column (
            modifier
                .fillMaxWidth()
                .padding(all = 15.dp)
        ){
            Row (
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                //Uygulama logosu
                Box(modifier = modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))){
                    Image(painter = painterResource(id = R.drawable.sepet_logo), contentDescription = "")
                }
                // Sipariş tarihi
                Text(text = date)
                if (open.value){
                    IconButton(onClick = { open.value = !open.value }) {
                        Icon(Icons.Filled.KeyboardArrowUp, contentDescription = "")
                    }
                }else{
                    IconButton(onClick = { open.value = !open.value }) {
                        Icon(Icons.Filled.KeyboardArrowDown, contentDescription = "")
                    }
                }

            }
            Spacer(modifier = modifier.height(8.dp))
            /**
             * Card içinde görsellerin,fiyat isim ve adetlerin yer aldığı yapı
             */
            if (open.value){
                list.forEach { order->
                    val url = "http://kasimadalan.pe.hu/yemekler/resimler/${order.resim}"
                    Divider(
                        modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp, bottom = 10.dp),
                        color = Color.LightGray
                    )
                    Row (
                        modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        GlideImage(
                            imageModel = url,
                            modifier
                                .width(80.dp)
                                .height(80.dp)
                                .weight(40f),
                            contentScale = ContentScale.Fit
                        )
                        Column (
                            modifier.weight(60f)
                        ){
                            Text(text = order.ad, fontWeight = FontWeight.Bold)
                            Text(text = stringResource(R.string.price) + order.fiyat.toString() + " ₺")
                            Text(text = stringResource(R.string.piece) + order.adet.toString())
                        }
                    }
                }
                Divider(
                    modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp),
                    color = Color.LightGray
                )
                /**
                 * Sepet tutarı ve sipariş verilen saat
                 */
                Text(text = stringResource(R.string.order_time, hours), modifier = modifier.fillMaxWidth(), textAlign = TextAlign.Start)
                Spacer(modifier = modifier.height(2.dp))
                Text(text = stringResource(R.string.order_total, total), modifier = modifier.fillMaxWidth(), textAlign = TextAlign.Start, fontWeight = FontWeight.Bold)

            }
        }
    }
}
