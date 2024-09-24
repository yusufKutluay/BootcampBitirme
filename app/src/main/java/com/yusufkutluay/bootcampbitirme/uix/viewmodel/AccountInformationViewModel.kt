package com.yusufkutluay.bootcampbitirme.uix.viewmodel

import androidx.lifecycle.ViewModel
import com.yusufkutluay.bootcampbitirme.data.entity.Users
import com.yusufkutluay.bootcampbitirme.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountInformationViewModel @Inject constructor(var frepo : FoodsRepository) : ViewModel() {

    fun getAllInformationById(id:String,callback: (List<Users>) -> Unit){
        frepo.getAllInformationById(id,callback)
    }
}