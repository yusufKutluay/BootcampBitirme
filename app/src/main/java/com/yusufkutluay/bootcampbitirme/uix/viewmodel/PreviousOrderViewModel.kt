package com.yusufkutluay.bootcampbitirme.uix.viewmodel

import androidx.lifecycle.ViewModel
import com.yusufkutluay.bootcampbitirme.data.entity.OrderPrevious
import com.yusufkutluay.bootcampbitirme.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviousOrderViewModel @Inject constructor(var frepo : FoodsRepository): ViewModel() {

    fun getPreviousOrder(userId : String,callback: (List<OrderPrevious>?) -> Unit){
        CoroutineScope(Dispatchers.Main).launch {
            frepo.getPreviousOrder(userId,callback)
        }
    }

}