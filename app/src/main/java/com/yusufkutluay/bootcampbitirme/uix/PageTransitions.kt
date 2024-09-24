package com.yusufkutluay.bootcampbitirme.uix

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.BasketViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.HomePageViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.ProfilViewModel
import com.yusufkutluay.bootcampbitirme.uix.views.basket.BasketPage
import com.yusufkutluay.bootcampbitirme.uix.views.home.HomePage
import com.yusufkutluay.bootcampbitirme.uix.views.profil.ProfilPage

@Composable
fun PageTransitions(
    modifier: Modifier,
    navController: NavController,
    homePageViewModel: HomePageViewModel,
    basketViewModel: BasketViewModel,
    profilViewModel: ProfilViewModel,
    context: Context,
    currentUseId: String?
) {
    NavHost(navController = navController as NavHostController, startDestination = "home") {
        composable("home") {
            HomePage(
                modifier,
                navController = navController,
                homePageViewModel = homePageViewModel,
                context,
                currentUseId
            ) }
        composable("basket") { BasketPage(modifier, basketViewModel = basketViewModel,context,
            currentUseId!!
        ) }
        composable("profil"){ ProfilPage(modifier, profilViewModel = profilViewModel,context,currentUseId,navController)}
    }
}