package com.yusufkutluay.bootcampbitirme.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yusufkutluay.bootcampbitirme.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (var frepo : FoodsRepository) : ViewModel() {

    fun signInUser(email: String, password: String, callback: (Boolean) -> Unit){
        frepo.signInUser(email,password,callback)
    }

}