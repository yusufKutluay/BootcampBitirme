package com.yusufkutluay.bootcampbitirme.uix.views.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.yusufkutluay.bootcampbitirme.MainActivity
import com.yusufkutluay.bootcampbitirme.R
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeDark
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeLight
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.SignupViewModel

@Composable
fun SignupPage(
    modifier: Modifier,
    navController: NavController,
    context: Context,
    signupViewModel: SignupViewModel
){

    val tfFullName = remember { mutableStateOf("") }
    val tfUserName = remember { mutableStateOf("") }
    val tfEmail = remember { mutableStateOf("") }
    val tfPassword = remember { mutableStateOf("") }
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    val screenHeightDp = configuration.screenHeightDp
    val size = screenWidthDp * screenHeightDp

     Scaffold {paddingValues ->
        Column(
            modifier
                .fillMaxSize()
                .background(ThemeDark)
                .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            /**
             * geri dönüş için icon
             */
            Column(
                modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, end = 15.dp),
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.Close, contentDescription = "", tint = ThemeLight, modifier = modifier.size(40.dp))
                }
            }

            /**
             * Uygulama Logosu
             */

            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.sepetle_name),
                contentDescription = "",
                modifier = modifier
                    .size((size / 1250).dp)
                    .weight(1f),
                contentScale = ContentScale.Crop
            )


            /***
             * Login işlemleri için card yapısı
             */
            Card(
                shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
            ) {
                Column(
                    modifier = modifier
                        .background(Color.White),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = modifier.height(20.dp))
                    Column {
                        /**
                         * Her kullanıcı girişi için textField yapısı
                         */
                        TextandCardSign(modifier = modifier, title = stringResource(R.string.signup_fullname), tf = tfFullName, keyboardType = KeyboardType.Text)
                        Spacer(modifier = modifier.height(10.dp))
                        TextandCardSign(modifier = modifier, title = stringResource(R.string.signup_username), tf = tfUserName, keyboardType = KeyboardType.Text)
                        Spacer(modifier = modifier.height(10.dp))
                        TextandCardSign(modifier = modifier, title = stringResource(R.string.email), tf = tfEmail, keyboardType = KeyboardType.Email)
                        Spacer(modifier = modifier.height(10.dp))
                        TextandCardSign(modifier = modifier, title = stringResource(R.string.password), tf = tfPassword, keyboardType = KeyboardType.Text)
                    }

                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 50.dp)
                    ){
                        /**
                         * Kullanıcın giriş yapması için buton
                         */
                        ElevatedButton(
                            onClick = {
                                if (tfEmail.value != "" && tfPassword.value != "" && tfFullName.value != "" && tfUserName.value != ""){
                                    signupViewModel.createUserWithEmail(tfEmail.value,tfPassword.value,tfFullName.value,tfUserName.value){callback ->

                                        println(callback)
                                        if (callback ){
                                            val intent = Intent(context,MainActivity::class.java)
                                            context.startActivity(intent)
                                            (context as Activity).finish()
                                        }else{
                                            Toast.makeText(context,
                                                context.getString(R.string.signup_warning_comment),Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }else{
                                    Toast.makeText(context,context.getString(R.string.fullfield_warning),Toast.LENGTH_SHORT).show()

                                }

                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = ThemeLight
                            ),
                            shape = RoundedCornerShape(8.dp),
                            modifier = modifier
                                .padding(top = 20.dp)
                                .width(250.dp)
                        ) {
                            Text(text = stringResource(R.string.signup_signup), color = ThemeDark, fontSize = 16.sp, modifier = modifier
                                .padding(bottom = 8.dp)
                                .fillMaxWidth(), textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}