package com.yusufkutluay.bootcampbitirme.uix.views.login

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.LoginViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.SignupViewModel

@Composable
fun PageTransitionsLog (
    context: Context,
    modifier: Modifier,
    signupViewModel: SignupViewModel,
    loginViewModel: LoginViewModel
){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login"){
        composable("login"){ LoginPage(context = context, modifier = modifier,navController = navController,loginViewModel) }
        composable("signup"){ SignupPage(modifier = modifier, navController = navController, context = context,signupViewModel) }
    }

}