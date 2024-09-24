package com.yusufkutluay.bootcampbitirme.uix.viewmodel

import androidx.lifecycle.ViewModel
import com.yusufkutluay.bootcampbitirme.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfilViewModel @Inject constructor(var frepo : FoodsRepository) : ViewModel(){

    fun getUserNameById(id:String,callback: (String) -> Unit){
        frepo.getUserNameById(id,callback)
    }


}