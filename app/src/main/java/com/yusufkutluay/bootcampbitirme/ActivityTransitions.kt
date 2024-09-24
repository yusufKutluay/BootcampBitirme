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
import com.google.gson.Gson
import com.yusufkutluay.bootcampbitirme.data.entity.Foods
import com.yusufkutluay.bootcampbitirme.ui.theme.BootcampBitirmeTheme
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.AccountInformationViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.PreviousOrderViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.FoodDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityTransitions : ComponentActivity() {

    private val previousOrderViewModel: PreviousOrderViewModel by viewModels()
    private val accountInformationViewModel: AccountInformationViewModel by viewModels()
    private val foodDetailsViewModel: FoodDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val intent = intent
            val jsonList: Foods? = try {
                val objectFood = intent.getStringExtra("objectFood")
                if (!objectFood.isNullOrEmpty()) {
                    Gson().fromJson(objectFood, Foods::class.java)
                } else {
                    null
                }
            } catch (e: Exception) {
                println("Parsing error: ${e.message}")
                null
            }

            val startDestination = intent.getStringExtra("start_destination") ?: return@setContent
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: return@setContent

            val navController = rememberNavController()
            BootcampBitirmeTheme {
                PageActivity(
                    navController = navController,
                    startDestination = startDestination,
                    modifier = Modifier,
                    context = context,
                    currentUserId = currentUserId,
                    previousOrderViewModel = previousOrderViewModel,
                    accountInformationViewModel = accountInformationViewModel,
                    foodDetailsViewModel = foodDetailsViewModel,
                    objectFood = jsonList
                )
            }
        }
    }
}
