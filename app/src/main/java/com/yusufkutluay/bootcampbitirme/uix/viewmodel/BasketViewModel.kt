package com.yusufkutluay.bootcampbitirme.uix.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yusufkutluay.bootcampbitirme.data.entity.Basket
import com.yusufkutluay.bootcampbitirme.data.repo.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BasketViewModel @Inject constructor(var frepo : FoodsRepository)  : ViewModel(){

    val basketData = MutableLiveData<List<Basket>>()

    fun basketUpload(kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            basketData.value = frepo.basketUpload(kullanici_adi)
        }
    }

    fun basketDelete(sepet_yemek_id:Int,kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            frepo.basketDelete(sepet_yemek_id,kullanici_adi)
            basketUpload(kullanici_adi)
        }
    }

    fun deleteAllByYemekAdi(yemekAdi: String, kullanici_adi: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val basketItems = frepo.basketUpload(kullanici_adi)

            basketItems.forEach { item ->
                frepo.basketDelete(item.sepet_yemek_id, kullanici_adi)
            }

            basketUpload(kullanici_adi)
        }
    }

    fun saveOrderListFirebase(total : String,listOrder : List<Basket>){
        CoroutineScope(Dispatchers.Main).launch {
            frepo.saveOrderListFirebase(total,listOrder)
        }
    }



}