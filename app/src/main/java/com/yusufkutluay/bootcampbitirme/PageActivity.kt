package com.yusufkutluay.bootcampbitirme

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yusufkutluay.bootcampbitirme.data.entity.Foods
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.AccountInformationViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.FoodDetailsViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.PreviousOrderViewModel
import com.yusufkutluay.bootcampbitirme.uix.views.home.FoodDetails
import com.yusufkutluay.bootcampbitirme.uix.views.profil.AccountInformation
import com.yusufkutluay.bootcampbitirme.uix.views.profil.PreviousOrder

@Composable
fun PageActivity (
    navController: NavController,
    startDestination: String,
    modifier: Modifier,
    context : Context,
    currentUserId : String,
    previousOrderViewModel: PreviousOrderViewModel,
    accountInformationViewModel : AccountInformationViewModel,
    objectFood : Foods?,
    foodDetailsViewModel : FoodDetailsViewModel
){

    NavHost(navController = navController as NavHostController, startDestination = startDestination) {
        composable("order_previous"){
            PreviousOrder(
                modifier,
                navController,
                currentUserId = currentUserId,
                previousOrderViewModel,
                context = context
            ) }
        composable("account"){
            AccountInformation(
                modifier = modifier,
                navController,
                currentUserId,
                accountInformationViewModel,
                context
            ) }

        if (objectFood != null){
            composable("food_detail"){
                FoodDetails(
                    modifier,
                    navController,
                    foodDetailsViewModel = foodDetailsViewModel,
                    objectFood,
                    context,
                    currentUserId
                )
            }
        }

    }
}