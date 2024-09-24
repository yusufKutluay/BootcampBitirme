package com.yusufkutluay.bootcampbitirme

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.yusufkutluay.bootcampbitirme.ui.theme.ThemeActivityLogin
import com.yusufkutluay.bootcampbitirme.uix.views.login.PageTransitionsLog
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.LoginViewModel
import com.yusufkutluay.bootcampbitirme.uix.viewmodel.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityLogin : ComponentActivity() {
    val signupViewModel: SignupViewModel by viewModels()
    val loginViewModel : LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ThemeActivityLogin {
                val context = LocalContext.current

                /**
                 * Kullanıcı giriş yapmış mı kontrol ediyoruz eğer yaptıysa
                 * giriş ekranı gelmez direk anasayfaya yönlendirir
                 */
                val currentUser = FirebaseAuth.getInstance().currentUser
                if (currentUser != null) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    PageTransitionsLog(context,Modifier,signupViewModel,loginViewModel)
                }

            }
        }
    }
}



