package com.yusufkutluay.bootcampbitirme.uix.views.profil

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.yusufkutluay.bootcampbitirme.ActivityLogin
import com.yusufkutluay.bootcampbitirme.R
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeDark
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.AccountInformationViewModel

@Composable
fun AccountInformation(
    modifier: Modifier,
    navController: NavController,
    currentUseId: String?,
    accountInformationViewModel: AccountInformationViewModel,
    context: Context
){
    val tfFullName = remember { mutableStateOf("") }
    val tfUserName = remember { mutableStateOf("") }
    val tfEmail = remember { mutableStateOf("") }
    val tfPassword = remember { mutableStateOf("") }
    val alertState = remember { mutableStateOf(false) }

    val auth = FirebaseAuth.getInstance()

    Scaffold {paddingValues ->
        Column(
            modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            accountInformationViewModel.getAllInformationById(currentUseId!!){callback->
                tfFullName.value = callback[0].fullName!!
                tfUserName.value = callback[0].userName!!
                tfEmail.value = callback[0].email!!
                tfPassword.value = callback[0].password!!
            }

            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                IconButton(
                    onClick = { (context as Activity).finish() },
                    modifier.padding(all = 15.dp)
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "",modifier.size(35.dp), tint = ThemeDark)
                }
            }

            CardInformation(modifier,tfFullName, stringResource(R.string.name_surname))
            CardInformation(modifier,tfUserName, stringResource(R.string.username))
            CardInformation(modifier,tfEmail, stringResource(R.string.email))
            CardInformation(modifier,tfPassword,stringResource(R.string.password))
            
            AlertView(alertState = alertState,auth,context)

            Column (
                modifier
                    .fillMaxWidth()
                    .padding(50.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                ElevatedButton(
                    onClick = { alertState.value = true },
                    colors = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.Red
                    ),
                    modifier = modifier.fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.delete_account), fontSize = 16.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun AlertView(
    alertState: MutableState<Boolean>,
    auth: FirebaseAuth,
    context: Context
){
    if (alertState.value){
        AlertDialog(
            onDismissRequest = { alertState.value = false },
            title = {
                Text(text = stringResource(id = R.string.warning))
            },
            text = {
                Text(text = stringResource(R.string.accoun_alert_text))
            },
            confirmButton = {
                Button(onClick = {
                    auth.currentUser?.delete()
                    auth.signOut()
                    val intent = Intent(context, ActivityLogin::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                    (context as Activity).finish()

                    alertState.value = false
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                    ) {
                    Text(text = stringResource(id = R.string.yes), color = Color.Black)
                }
            },
            dismissButton = {
                Button(
                    onClick = { alertState.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Text(text = stringResource(id = R.string.no), color = Color.Black)
                }
            },
            containerColor = Color.White
        )
    }

    
}

@Composable
fun CardInformation(
    modifier: Modifier,
    tf : MutableState<String>,
    title : String
){
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ){
        Text(text = title,
            modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 50.dp))
        OutlinedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, start = 40.dp, end = 40.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x3CA8A8A8)
            )
        ) {
            Text(text = tf.value, modifier = modifier.fillMaxWidth().padding(all = 15.dp))
        }
    }
}