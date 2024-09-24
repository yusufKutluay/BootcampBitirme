package com.yusufkutluay.bootcampbitirme.uix.views.profil

import LottieAnimationExample
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.yusufkutluay.bootcampbitirme.ActivityLogin
import com.yusufkutluay.bootcampbitirme.ActivityTransitions
import com.yusufkutluay.bootcampbitirme.R
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeDark
import com.yusufkutluay.bootcampbitirme.ui.theme.roboto_slab
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.ProfilViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilPage(
    modifier: Modifier,
    profilViewModel: ProfilViewModel,
    context: Context,
    currentUserId: String?,
    navController: NavController
){

    val auth = FirebaseAuth.getInstance()
    val findUserName = auth.currentUser?.uid
    val fullName = remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    val size = screenHeight * screenWidth  // genişlik ve yüksekliği çarparak kaç piksel lduğunu buluyoruz
    val isAlertState = remember { mutableStateOf(false) }
    val alertControlTitle = remember { mutableStateOf("") }
    val modalBottomSheetState = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    Column (
        modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Butona tıklandığında fullName'in değerini güncelle
        profilViewModel.getUserNameById(findUserName!!) { callback ->
            fullName.value = callback
        }

        Spacer(modifier = modifier.height(15.dp))
        Box(modifier = modifier.size((size/2500).dp)){
            LottieAnimationExample(R.raw.anim_profile)
        }
        Text(text = fullName.value, fontSize = 20.sp, fontFamily = roboto_slab, color = ThemeDark)
        Divider(
            modifier
                .fillMaxWidth()
                .padding(top = 20.dp, bottom = 20.dp, start = 40.dp, end = 40.dp),
            color = Color.LightGray
        )
        Column(
            modifier.fillMaxWidth()
        ) {
            TextLog(modifier = modifier,context.getString(R.string.account_ınformation),Icons.Filled.Person,isAlertState,context,alertControlTitle,navController,modalBottomSheetState)
            TextLog(modifier = modifier, title = context.getString(R.string.previous_order), icon = Icons.Filled.ShoppingCart,isAlertState,context,alertControlTitle,navController,modalBottomSheetState)
            TextLog(modifier = modifier, title = "- Kullanıcı sözleşmesi", icon = Icons.Filled.Info,isAlertState,context,alertControlTitle,navController,modalBottomSheetState)
            TextLog(modifier = modifier, title = context.getString(R.string.log_out), icon = Icons.Filled.ExitToApp,isAlertState,context,alertControlTitle,navController,modalBottomSheetState)
        }

        AlertDialogWindow(isAlertState,auth,context,alertControlTitle)
        ModalBottomSheetField(onDismissRequest = modalBottomSheetState,sheetState)
    }
}

@Composable
fun TextLog(
    modifier: Modifier,
    title : String,
    icon : ImageVector,
    isAlertState: MutableState<Boolean>,
    context: Context,
    alertControlTitle : MutableState<String>,
    navController: NavController,
    modalBottomSheetState : MutableState<Boolean>
){
    Row (
        modifier
            .fillMaxWidth()
            .clickable {
                if (title == context.getString(R.string.log_out)) {
                    alertControlTitle.value = context.getString(R.string.log_out)
                    isAlertState.value = true
                }
                if (title == context.getString(R.string.account_ınformation)) {
                    val intent = Intent(context, ActivityTransitions::class.java)
                    intent.putExtra("start_destination", "account")
                    context.startActivity(intent)
                }
                if (title == context.getString(R.string.previous_order)) {
                    val intent = Intent(context, ActivityTransitions::class.java)
                    intent.putExtra("start_destination", "order_previous")
                    context.startActivity(intent)
                }
                if (title == "- Kullanıcı sözleşmesi") {
                    modalBottomSheetState.value = true
                }

            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = title, fontSize = 18.sp, modifier = modifier.padding(15.dp))
        Icon(icon, contentDescription = "",modifier.padding(end = 20.dp))
    }
    Divider(
        modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
        color = Color.LightGray
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModalBottomSheetField(
    onDismissRequest: MutableState<Boolean>,
    sheetState: SheetState
) {
    if (onDismissRequest.value){
        ModalBottomSheet(
            onDismissRequest = { onDismissRequest.value = false },
            sheetState = sheetState,
            containerColor = Color.White
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.user_agreement),
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Scrollable text
                LazyColumn(
                    modifier = Modifier.weight(1f) // Allow the column to take available space
                ) {
                    item {
                        Text(
                            text = stringResource(id = R.string.aciklama_sözlesme),
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }
                }
                TextButton(
                    onClick = { onDismissRequest.value = false },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = stringResource(R.string.close))
                }
            }
        }
    }

}

@Composable
fun AlertDialogWindow(
    isAlertState: MutableState<Boolean>,
    auth: FirebaseAuth,
    context: Context,
    alertControlTitle : MutableState<String>
) {
    if (isAlertState.value){
        AlertDialog(
            onDismissRequest = { isAlertState.value = false},
            confirmButton = {
                TextButton(onClick = {
                    if (alertControlTitle.value == context.getString(R.string.log_out)){
                        auth.signOut()
                        val intent = Intent(context,ActivityLogin::class.java)
                        context.startActivity(intent)
                        (context as Activity).finish()
                    }
                    isAlertState.value = false
                }) {
                    Text(stringResource(R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    // "İptal" butonuna tıklandığında yapılacak işlemler
                    isAlertState.value = false
                }) {
                    Text(stringResource(R.string.no))
                }
            },
            title = {
                Text(text = stringResource(R.string.warning))
            },
            text = {
                Text(stringResource(R.string.alertd_title))
            },
            containerColor = Color(0xFFDBCDCD)
        )
    }
}
