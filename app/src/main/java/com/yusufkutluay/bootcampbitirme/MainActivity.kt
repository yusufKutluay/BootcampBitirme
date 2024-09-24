package com.yusufkutluay.bootcampbitirme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.yusufkutluay.bootcampbitirme.ui.theme.BootcampBitirmeTheme
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.BasketViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.HomePageViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.ProfilViewModel
import com.yusufkutluay.bootcampbitirme.uix.BottomBarPage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val homePageViewModel: HomePageViewModel by viewModels()
    val basketViewModel : BasketViewModel by viewModels()
    val profilViewModel : ProfilViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BootcampBitirmeTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
                    BottomBarPage(
                        modifier = Modifier,
                        navController = navController,
                        homePageViewModel = homePageViewModel,
                        basketViewModel = basketViewModel,
                        profilViewModel = profilViewModel,
                        context = context,
                        currentUserId = currentUserId,
                        )
            }
        }
    }
}