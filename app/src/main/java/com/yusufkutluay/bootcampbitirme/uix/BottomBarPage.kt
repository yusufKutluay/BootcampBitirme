package com.yusufkutluay.bootcampbitirme.uix

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yusufkutluay.bootcampbitirme.R
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeDark
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeLight
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.BasketViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.HomePageViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.ProfilViewModel

@Composable
fun BottomBarPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    homePageViewModel: HomePageViewModel,
    basketViewModel: BasketViewModel,
    profilViewModel: ProfilViewModel,
    context: Context,
    currentUserId: String?,
    ) {
    val secilenItem = remember { mutableStateOf(0) }
    val configuration = LocalConfiguration.current // ekran boyutunu almak için localconfiguration kullanıyoruz
    val screenHeight = configuration.screenWidthDp

    // Sepet verilerini gözlemle
    val basketData = basketViewModel.basketData.observeAsState(listOf())

    val userName = remember { mutableStateOf(currentUserId) }


    LaunchedEffect(key1 = true) {
        basketViewModel.basketUpload(userName.value!!)
    }

    // Total ürün sayısını hesapla
    val totalProducts = basketData.value
        .groupBy { it.yemek_adi }
        .count()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomAppBar (
                modifier
                    .height((screenHeight / 4).dp)
                    .clip(
                        RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    ),
                containerColor = Color.White
            ){
                Column(
                    modifier.fillMaxSize()
                ) {
                    Divider(modifier.fillMaxWidth(), color = Color.LightGray)
                    Row {
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    Icons.Default.Home,
                                    contentDescription = "Home"
                                )
                            },
                            label = { if (secilenItem.value == 0) Text(stringResource(R.string.bottombar_home), fontSize = 13.sp) },
                            selected = secilenItem.value == 0,
                            onClick = {
                                homePageViewModel.basketUpload(currentUserId!!)
                                secilenItem.value = 0
                                      },
                            colors = NavigationBarItemDefaults.colors(
                                unselectedIconColor = ThemeLight,
                                selectedIconColor = ThemeDark,
                                indicatorColor = Color.Transparent
                            )
                        )
                        NavigationBarItem(
                            selected = secilenItem.value == 1,
                            onClick = { secilenItem.value = 1 },
                            icon = {
                                BadgedBox(badge = {
                                    Badge { if (totalProducts == null) Text("0") else Text(totalProducts.toString()) }  // 3 ürünü sepet içinde göster
                                }) {
                                    Icon(Icons.Filled.ShoppingCart, contentDescription = "")
                                }
                            },
                            label = { if (secilenItem.value == 1) Text(text = stringResource(id = R.string.basketpage_title), fontSize = 13.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                unselectedIconColor = ThemeLight,
                                selectedIconColor = ThemeDark,
                                indicatorColor = Color.Transparent
                            )
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Default.Person, contentDescription = "") },
                            label = { if (secilenItem.value == 2) Text(stringResource(id = R.string.bottombar_profile), fontSize = 13.sp, color = ThemeDark) },
                            selected = secilenItem.value == 2,
                            onClick = { secilenItem.value = 2 },
                            colors = NavigationBarItemDefaults.colors(
                                unselectedIconColor = ThemeLight,
                                selectedIconColor = ThemeDark,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }

                }

            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues), // Burada paddingValues'i kullanıyoruz
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PageTransitions(
                modifier = modifier,
                navController = navController,
                homePageViewModel = homePageViewModel,
                basketViewModel = basketViewModel,
                profilViewModel = profilViewModel,
                context = context,
                currentUseId = currentUserId
            )

            // sayfa değişimlerine göre yönlendirme
            when (secilenItem.value) {
                0 -> navController.navigate("home")
                1 -> navController.navigate("basket")
                2 -> navController.navigate("profil")
            }
        }
    }
}


