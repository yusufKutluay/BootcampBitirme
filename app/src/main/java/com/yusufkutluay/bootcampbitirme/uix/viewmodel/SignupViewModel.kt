package com.yusufkutluay.bootcampbitirme.uix.viewmodel

import androidx.lifecycle.ViewModel
import com.yusufkutluay.bootcampbitirme.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor (var frepo : FoodsRepository) : ViewModel() {

    fun createUserWithEmail(email: String, password: String, fullName: String,userName:String, callback: (Boolean) -> Unit){
        frepo.createUserWithEmail(email,password,fullName,userName,callback)
    }

}